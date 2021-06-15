package com.katyrin.loan_online.ui.loanconditions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.katyrin.loan_online.R
import com.katyrin.loan_online.data.model.LoanConditionsDTO
import com.katyrin.loan_online.databinding.FragmentLoanConditionsBinding
import com.katyrin.loan_online.ui.activities.OnAppCompatActivity
import com.katyrin.loan_online.ui.loanrequest.LoanRequestFragment
import com.katyrin.loan_online.utils.showErrorMessage
import com.katyrin.loan_online.utils.toast
import com.katyrin.loan_online.viewmodel.LoanConditionsViewModel
import com.katyrin.loan_online.viewmodel.appstates.RequestState
import javax.inject.Inject

class LoanConditionsFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val loanConditionsViewModel: LoanConditionsViewModel by viewModels(factoryProducer = { factory })
    private var binding: FragmentLoanConditionsBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as OnAppCompatActivity).appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoanConditionsBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loanConditionsViewModel.requestState.observe(viewLifecycleOwner) {
            handleRequestResult(it)
        }
        loanConditionsViewModel.getLoanConditions()
    }

    private fun setButtonClick(loanConditionsDTO: LoanConditionsDTO) {
        binding?.agreeButton?.setOnClickListener {
            if (binding?.agreeButton?.isEnabled == true) {
                val maxAmount: Int = loanConditionsDTO.maxAmount!!
                val percent: Double = loanConditionsDTO.percent!!
                val period: Int = loanConditionsDTO.period!!
                replaceLoanRequestFragment(maxAmount, percent, period)
            }
        }
    }

    private fun replaceLoanRequestFragment(maxAmount: Int, percent: Double, period: Int) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, LoanRequestFragment.newInstance(maxAmount, percent, period))
            .commitNow()
    }

    private fun handleRequestResult(state: RequestState<LoanConditionsDTO>) {
        when (state) {
            is RequestState.Success -> {
                setInfo(state.value)
                setButtonClick(state.value)
                visibleScreenState()
            }
            is RequestState.Loading -> {
                loadingState()
            }
            is RequestState.ServerError -> {
                visibleScreenState()
                requireContext().toast(getString(R.string.server_error))
            }
            is RequestState.ClientError -> {
                visibleScreenState()
                requireContext().showErrorMessage(state.code)
            }
        }
    }

    private fun loadingState() {
        binding?.apply {
            loading.isVisible = true
            agreeButton.isVisible = false
            loanTermsTextView.isVisible = false
            maxAmountTextView.isVisible = false
            percentTextView.isVisible = false
            periodTextView.isVisible = false
        }
    }

    private fun visibleScreenState() {
        binding?.apply {
            loading.isVisible = false
            agreeButton.isVisible = true
            loanTermsTextView.isVisible = true
            maxAmountTextView.isVisible = true
            percentTextView.isVisible = true
            periodTextView.isVisible = true
        }
    }

    private fun setInfo(loanConditionsDTO: LoanConditionsDTO) {
        val maxAmount = "${getString(R.string.max_amount)} ${loanConditionsDTO.maxAmount}"
        val percent =
            "${getString(R.string.text_percent)} " + loanConditionsDTO.percent + getString(R.string.percent_symbol)
        val period = "${getString(R.string.text_period)} ${loanConditionsDTO.period}"
        binding?.apply {
            maxAmountTextView.text = maxAmount
            percentTextView.text = percent
            periodTextView.text = period
        }
    }

    override fun onDetach() {
        binding = null
        super.onDetach()
    }

    companion object {
        const val TAG = "LoanConditionsFragment"
        fun newInstance() = LoanConditionsFragment()
    }
}