package com.katyrin.loan_online.ui.success

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.katyrin.loan_online.ui.activities.AuthorizedActivity
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.databinding.FragmentSuccessBinding

class SuccessFragment : Fragment() {

    private var loanDTO: LoanDTO? = null
    private var binding: FragmentSuccessBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            loanDTO = it.getParcelable(LOAN_DTO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSuccessBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setButtonClick()
    }

    private fun setButtonClick() {
        binding?.okButton?.setOnClickListener {
            startActivity(Intent(requireContext(), AuthorizedActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun initViews() {
        binding?.apply {
            amountTextView.text = loanDTO?.amount.toString()
            dateTextView.text = loanDTO?.date.toString()
            firstNameTextView.text = loanDTO?.firstName.toString()
            idTextView.text = loanDTO?.id.toString()
            lastNameTextView.text = loanDTO?.lastName.toString()
            percentTextView.text = loanDTO?.percent.toString()
            periodTextView.text = loanDTO?.period.toString()
            phoneNumberTextView.text = loanDTO?.phoneNumber.toString()
            stateTextView.text = loanDTO?.state.toString()
        }
    }

    override fun onDetach() {
        loanDTO = null
        binding = null
        super.onDetach()
    }

    companion object {
        private const val LOAN_DTO = "LOAN_DTO"
        fun newInstance(loanDTO: LoanDTO) =
            SuccessFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LOAN_DTO, loanDTO)
                }
            }
    }
}