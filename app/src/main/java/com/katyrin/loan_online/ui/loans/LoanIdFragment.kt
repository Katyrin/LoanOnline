package com.katyrin.loan_online.ui.loans

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.katyrin.loan_online.R
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.model.LoanState
import com.katyrin.loan_online.databinding.FragmentLoanIdBinding
import com.katyrin.loan_online.ui.activities.OnAppCompatActivity
import com.katyrin.loan_online.viewmodel.loanrequest.LoanRequestState
import com.katyrin.loan_online.viewmodel.loans.LoanIdViewModel
import javax.inject.Inject

class LoanIdFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val loanIdViewModel: LoanIdViewModel by viewModels(factoryProducer = { factory })
    private var binding: FragmentLoanIdBinding? = null
    private var id: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as OnAppCompatActivity).appComponent?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt(LOAN_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoanIdBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loanIdViewModel.loanRequestState.observe(viewLifecycleOwner) { renderData(it) }
        loanIdViewModel.dateText.observe(viewLifecycleOwner) { setDateText(it) }
        id?.let { loanIdViewModel.getLoanInfo(it) }
    }

    private fun renderData(state: LoanRequestState) {
        when (state) {
            is LoanRequestState.Success -> setSuccessState(state.loanDTO)
            is LoanRequestState.Loading -> setLoadingState()
            is LoanRequestState.Error -> setErrorState()
        }
    }

    private fun setSuccessState(loanDTO: LoanDTO) {
        binding?.progressBar?.isVisible = false
        binding?.scrollView?.isVisible = true
        loanIdViewModel.getDateText(loanDTO.date)
        setInfo(loanDTO)
        setStateLoan(loanDTO.state)
    }

    private fun setLoadingState() {
        binding?.progressBar?.isVisible = true
        binding?.scrollView?.isVisible = false
    }

    private fun setErrorState() {
        binding?.progressBar?.isVisible = false
        binding?.scrollView?.isVisible = true
        showRequestFailed()
    }

    private fun setDateText(date: Pair<String, String>) {
        val dateText =
            "${getString(R.string.detailed_information_from)} ${date.first} \n${date.second}"
        binding?.dateTextView?.text = dateText
    }

    private fun setInfo(loanDTO: LoanDTO) {
        val id = "${getString(R.string.id)} ${loanDTO.id}"
        val firstName = "${getString(R.string.borrower_name)} ${loanDTO.firstName}"
        val lastName = "${getString(R.string.borrower_s_surname)} ${loanDTO.lastName}"
        val amount = "${getString(R.string.text_amount)} ${loanDTO.amount}"
        val percent =
            "${getString(R.string.text_percent)} " + loanDTO.percent + getString(R.string.percent_symbol)
        val period = "${getString(R.string.text_period)} ${loanDTO.period}"
        val phoneNumber = "${getString(R.string.phone_number)} ${loanDTO.phoneNumber}"

        binding?.apply {
            firstNameTextView.text = firstName
            lastNameTextView.text = lastName
            amountTextView.text = amount
            percentTextView.text = percent
            periodTextView.text = period
            phoneNumberTextView.text = phoneNumber
            idTextView.text = id
        }
    }

    private fun setStateLoan(state: LoanState) {
        when (state) {
            LoanState.APPROVED -> {
                binding?.infoTextView?.isVisible = true
                binding?.stateTextView?.text = getText(R.string.text_approved_state)
                binding?.stateTextView?.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.olivine)
                )
            }
            LoanState.REJECTED -> {
                binding?.infoTextView?.isVisible = false
                binding?.stateTextView?.text = getText(R.string.text_rejected_state)
                binding?.stateTextView?.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.rejected)
                )
            }
            LoanState.REGISTERED -> {
                binding?.infoTextView?.isVisible = false
                binding?.stateTextView?.text = getText(R.string.text_registered_state)
            }
        }
    }

    private fun showRequestFailed() {
        Toast.makeText(requireContext(), R.string.loan_request_failed, Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        id = null
        binding = null
        super.onDetach()
    }

    companion object {
        private const val LOAN_ID = "LOAN_ID"
        fun newInstance(id: Int) = LoanIdFragment().apply {
            arguments = Bundle().apply {
                putInt(LOAN_ID, id)
            }
        }
    }
}