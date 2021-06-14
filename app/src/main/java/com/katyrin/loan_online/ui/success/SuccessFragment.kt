package com.katyrin.loan_online.ui.success

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.katyrin.loan_online.R
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.databinding.FragmentSuccessBinding
import com.katyrin.loan_online.ui.activities.AuthorizedActivity
import com.katyrin.loan_online.ui.activities.OnAppCompatActivity
import com.katyrin.loan_online.ui.activities.OnHomeScreen
import com.katyrin.loan_online.viewmodel.success.SuccessViewModel
import javax.inject.Inject

class SuccessFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val successViewModel: SuccessViewModel by viewModels(factoryProducer = { factory })
    private var loanDTO: LoanDTO? = null
    private var binding: FragmentSuccessBinding? = null
    private var listener: OnHomeScreen? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as OnAppCompatActivity).appComponent?.inject(this)
        if (requireActivity() is AuthorizedActivity)
            listener = (context as AuthorizedActivity)
    }

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
        successViewModel.dateText.observe(viewLifecycleOwner) { setDateText(it) }
        loanDTO?.date?.let { successViewModel.getDateText(it) }
        setInfo()
        setButtonClick()
    }

    private fun setDateText(date: Pair<String, String>) {
        val dateText =
            "${getString(R.string.detailed_information_from)} ${date.first} \n${date.second}"
        binding?.dateTextView?.text = dateText
    }

    private fun setButtonClick() {
        binding?.okButton?.setOnClickListener {
            setButtonAction()
        }
    }

    private fun setButtonAction() {
        if (requireActivity() is AuthorizedActivity) {
            listener?.onHomeScreen()
        } else {
            startActivity(Intent(requireContext(), AuthorizedActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun setInfo() {
        val id = "${getString(R.string.id)} ${loanDTO?.id}"
        val firstName = "${getString(R.string.borrower_name)} ${loanDTO?.firstName}"
        val lastName = "${getString(R.string.borrower_s_surname)} ${loanDTO?.lastName}"
        val amount = "${getString(R.string.text_amount)} ${loanDTO?.amount}"
        val percent =
            "${getString(R.string.text_percent)} " + loanDTO?.percent + getString(R.string.percent_symbol)
        val period = "${getString(R.string.text_period)} ${loanDTO?.period}"
        val phoneNumber = "${getString(R.string.phone_number)} ${loanDTO?.phoneNumber}"

        binding?.apply {
            firstNameTextView.text = firstName
            lastNameTextView.text = lastName
            amountTextView.text = amount
            percentTextView.text = percent
            periodTextView.text = period
            phoneNumberTextView.text = phoneNumber
            idTextView.text = id
            stateTextView.text = getText(R.string.text_registered_state)
        }
    }

    override fun onDetach() {
        loanDTO = null
        binding = null
        if (requireActivity() is AuthorizedActivity)
            listener = null
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