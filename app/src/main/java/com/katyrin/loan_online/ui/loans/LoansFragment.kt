package com.katyrin.loan_online.ui.loans

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
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.databinding.FragmentLoansBinding
import com.katyrin.loan_online.ui.activities.OnAppCompatActivity
import com.katyrin.loan_online.viewmodel.loans.LoansRequestState
import com.katyrin.loan_online.viewmodel.loans.LoansViewModel
import javax.inject.Inject

class LoansFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val loansViewModel: LoansViewModel by viewModels(factoryProducer = { factory })
    private var binding: FragmentLoansBinding? = null
    private val prefs: Prefs by lazy { App.prefs!! }

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
        loansViewModel.loansRequestState.observe(viewLifecycleOwner) { renderData(it) }
        prefs.token?.let { loansViewModel.getLoans(it) }
    }

    private fun renderData(state: LoansRequestState) {
        when(state) {
            is LoansRequestState.Success -> {
                binding?.progressBar?.isVisible = false
                binding?.loansRecyclerView?.isVisible = true
                setRecyclerViewAdapter(state.loans)
            }
            is LoansRequestState.Loading -> {
                binding?.progressBar?.isVisible = true
                binding?.loansRecyclerView?.isVisible = false
            }
            is LoansRequestState.Error -> {
                binding?.progressBar?.isVisible = false
                binding?.loansRecyclerView?.isVisible = true
                showRequestFailed()
            }
        }
    }

    private fun setRecyclerViewAdapter(loans: List<LoanDTO>) {
        binding?.loansRecyclerView?.adapter = LoansRecyclerAdapter(loans) { addLoanIdFragment(it) }
    }

    private fun addLoanIdFragment(id: Int) {
        parentFragmentManager.beginTransaction()
            .add(R.id.container, LoanIdFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    private fun showRequestFailed() {
        Toast.makeText(requireContext(), R.string.loan_request_failed, Toast.LENGTH_SHORT).show()
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