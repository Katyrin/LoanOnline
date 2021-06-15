package com.katyrin.loan_online.ui.loanrequest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.katyrin.loan_online.R
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.model.LoanRequest
import com.katyrin.loan_online.databinding.FragmentLoanRequestBinding
import com.katyrin.loan_online.ui.activities.OnAppCompatActivity
import com.katyrin.loan_online.ui.success.SuccessFragment
import com.katyrin.loan_online.utils.afterTextChanged
import com.katyrin.loan_online.utils.showErrorMessage
import com.katyrin.loan_online.utils.toast
import com.katyrin.loan_online.viewmodel.LoanRequestViewModel
import com.katyrin.loan_online.viewmodel.appstates.ImportantDataState
import com.katyrin.loan_online.viewmodel.appstates.RequestState
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class LoanRequestFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val loanRequestViewModel: LoanRequestViewModel by viewModels(factoryProducer = { factory })
    private var binding: FragmentLoanRequestBinding? = null

    private val _textInput = BehaviorSubject.create<Triple<String, String, String>>()
    private val textInput = _textInput.toFlowable(BackpressureStrategy.LATEST)

    private var maxAmount: Int? = null
    private var percent: Double? = null
    private var period: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as OnAppCompatActivity).appComponent?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            maxAmount = it.getInt(MAX_AMOUNT)
            percent = it.getDouble(PERCENT)
            period = it.getInt(PERIOD)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoanRequestBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loanRequestViewModel.dataFormState.observe(viewLifecycleOwner) { handleImportantDataState(it) }
        loanRequestViewModel.requestState.observe(viewLifecycleOwner) { handleRequestResult(it) }
        loanRequestViewModel.subscribeImportantDataChanged(textInput)
        initViews()
    }

    private fun handleRequestResult(state: RequestState<LoanDTO>) {
        when (state) {
            is RequestState.Success -> {
                replaceSuccessFragment(state.value)
            }
            is RequestState.ServerError -> {
                binding?.scrollView?.isVisible = true
                binding?.progressBar?.isVisible = false
                requireContext().toast(getString(R.string.server_error))
            }
            is RequestState.ClientError -> {
                binding?.scrollView?.isVisible = true
                binding?.progressBar?.isVisible = false
                requireContext().showErrorMessage(state.code)
            }
            is RequestState.Loading -> {
                binding?.scrollView?.isVisible = false
                binding?.progressBar?.isVisible = true
            }
        }
    }

    private fun replaceSuccessFragment(loanDTO: LoanDTO) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SuccessFragment.newInstance(loanDTO))
            .commitNow()
    }

    private fun handleImportantDataState(state: ImportantDataState) {
        when (state) {
            is ImportantDataState.ErrorFirstName -> {
                binding?.firstNameEditText?.error = getString(R.string.invalid_first_name)
            }
            is ImportantDataState.ErrorLastName -> {
                binding?.lastNameEditText?.error = getString(R.string.invalid_last_name)
            }
            is ImportantDataState.ErrorPhoneNumber -> {
                binding?.phoneNumberEditText?.error = getString(R.string.invalid_phone_number)
            }
            is ImportantDataState.Success -> {
                binding?.sendRequestButton?.isEnabled = true
            }
        }
    }

    private fun initViews() {
        setText()
        setSeekBar()
        binding?.sendRequestButton?.setOnClickListener { sendLoanRequest() }
        binding?.firstNameEditText?.afterTextChanged { onNextTextInput() }
        binding?.lastNameEditText?.afterTextChanged { onNextTextInput() }
        binding?.phoneNumberEditText?.apply {
            afterTextChanged { onNextTextInput() }
            setOnEditorActionListener { _, _, _ ->
                sendLoanRequest()
                false
            }
        }
    }

    private fun setText() {
        val percent =
            "${getString(R.string.text_percent)} $percent ${getString(R.string.percent_symbol)}"
        val period = "${getString(R.string.text_period)} $period"
        binding?.percentTextView?.text = percent
        binding?.periodTextView?.text = period
    }

    private fun setSeekBar() {
        binding?.maxAmountSeekBar?.apply {
            max = maxAmount!!
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    val amount = "${getString(R.string.text_amount)} $progress"
                    binding?.maxAmountTextView?.text = amount
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
    }

    private fun onNextTextInput() {
        _textInput.onNext(
            Triple(
                binding?.firstNameEditText?.text.toString(),
                binding?.lastNameEditText?.text.toString(),
                binding?.phoneNumberEditText?.text.toString()
            )
        )
    }

    private fun sendLoanRequest() {
        if (binding?.sendRequestButton?.isEnabled == true) {
            loanRequestViewModel.sendRequest(getLoanRequestEntity())
        }
    }

    private fun getLoanRequestEntity(): LoanRequest =
        LoanRequest(
            binding?.maxAmountSeekBar?.progress,
            binding?.firstNameEditText?.text.toString(),
            binding?.lastNameEditText?.text.toString(),
            percent,
            period,
            binding?.phoneNumberEditText?.text.toString()
        )

    override fun onDetach() {
        binding = null
        super.onDetach()
    }

    companion object {
        private const val MAX_AMOUNT = "MAX_AMOUNT"
        private const val PERCENT = "PERCENT"
        private const val PERIOD = "PERIOD"

        fun newInstance(maxAmount: Int, percent: Double, period: Int) =
            LoanRequestFragment().apply {
                arguments = Bundle().apply {
                    putInt(MAX_AMOUNT, maxAmount)
                    putDouble(PERCENT, percent)
                    putInt(PERIOD, period)
                }
            }
    }
}