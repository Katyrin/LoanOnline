package com.katyrin.loan_online.ui.loanconditions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.katyrin.loan_online.App
import com.katyrin.loan_online.Prefs
import com.katyrin.loan_online.R
import com.katyrin.loan_online.data.model.LoanConditionsDTO
import com.katyrin.loan_online.databinding.FragmentLoanConditionsBinding
import com.katyrin.loan_online.ui.activities.OnAppCompatActivity
import com.katyrin.loan_online.ui.loanrequest.LoanRequestFragment
import com.katyrin.loan_online.viewmodel.loanconditions.LoanConditionsState
import com.katyrin.loan_online.viewmodel.loanconditions.LoanConditionsViewModel
import javax.inject.Inject

class LoanConditionsFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val loanConditionsViewModel: LoanConditionsViewModel by viewModels(factoryProducer = { factory })
    private var binding: FragmentLoanConditionsBinding? = null
    private val prefs: Prefs by lazy { App.prefs!! }

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
        loanConditionsViewModel.loanConditionsState.observe(viewLifecycleOwner) {
            handleRequestResult(it)
        }
        prefs.token?.let { loanConditionsViewModel.getLoanConditions(it) }
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

    private fun handleRequestResult(state: LoanConditionsState) {
        when (state) {
            is LoanConditionsState.Success -> {
                setViews(state.loanConditionsDTO)
                setButtonClick(state.loanConditionsDTO)
            }
            is LoanConditionsState.Loading -> {
                binding?.loading?.isVisible = true
                binding?.agreeButton?.isEnabled = false
            }
            is LoanConditionsState.Error -> {
                showRequestFailed()
            }
        }
    }

    private fun setViews(loanConditionsDTO: LoanConditionsDTO) {
        val maxAmount = "${getString(R.string.max_amount)} ${loanConditionsDTO.maxAmount}"
        val percent = "${getString(R.string.text_percent)} ${loanConditionsDTO.percent} " +
                getString(R.string.percent_symbol)
        val period = "${getString(R.string.text_period)} ${loanConditionsDTO.period}"
        binding?.apply {
            agreeButton.isEnabled = true
            loading.isVisible = false
            maxAmountTextView.text = maxAmount
            percentTextView.text = percent
            periodTextView.text = period
        }
    }

    private fun showRequestFailed() {
        binding?.loading?.isVisible = false
        Toast.makeText(
            requireContext(),
            R.string.loan_conditions_request_failed,
            Toast.LENGTH_SHORT
        ).show()
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