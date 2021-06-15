package com.katyrin.loan_online.ui.loans

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
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.databinding.FragmentLoansBinding
import com.katyrin.loan_online.ui.activities.OnAppCompatActivity
import com.katyrin.loan_online.utils.showErrorMessage
import com.katyrin.loan_online.utils.toast
import com.katyrin.loan_online.viewmodel.LoansViewModel
import com.katyrin.loan_online.viewmodel.appstates.RequestState
import javax.inject.Inject

class LoansFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val loansViewModel: LoansViewModel by viewModels(factoryProducer = { factory })
    private var binding: FragmentLoansBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as OnAppCompatActivity).appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoansBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        loansViewModel.requestState.observe(viewLifecycleOwner) { renderData(it) }
        loansViewModel.getLoans()
    }

    private fun initRecyclerView() {
        binding?.loansRecyclerView?.adapter = LoansRecyclerAdapter { addLoanIdFragment(it) }
    }

    private fun renderData(state: RequestState<List<LoanDTO>>) {
        when (state) {
            is RequestState.Success -> {
                binding?.progressBar?.isVisible = false
                binding?.loansRecyclerView?.isVisible = true
                updateList(state.value)
            }
            is RequestState.Loading -> {
                binding?.progressBar?.isVisible = true
                binding?.loansRecyclerView?.isVisible = false
            }
            is RequestState.ServerError -> {
                binding?.progressBar?.isVisible = false
                binding?.loansRecyclerView?.isVisible = true
                requireContext().toast(getString(R.string.server_error))
            }
            is RequestState.ClientError -> {
                binding?.progressBar?.isVisible = false
                binding?.loansRecyclerView?.isVisible = true
                requireContext().showErrorMessage(state.code)
            }
        }
    }

    private fun updateList(loans: List<LoanDTO>) {
        (binding?.loansRecyclerView?.adapter as LoansRecyclerAdapter).updateLoansList(loans)
    }

    private fun addLoanIdFragment(id: Int) {
        parentFragmentManager.beginTransaction()
            .add(R.id.container, LoanIdFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    override fun onDetach() {
        binding = null
        super.onDetach()
    }

    companion object {
        const val TAG = "LoansFragment"
        fun newInstance() = LoansFragment()
    }
}