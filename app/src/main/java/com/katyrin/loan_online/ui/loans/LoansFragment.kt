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
import androidx.work.*
import com.katyrin.loan_online.NotificationWorker
import com.katyrin.loan_online.R
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.databinding.FragmentLoansBinding
import com.katyrin.loan_online.ui.activities.OnAppCompatActivity
import com.katyrin.loan_online.utils.*
import com.katyrin.loan_online.viewmodel.LoansViewModel
import com.katyrin.loan_online.viewmodel.appstates.RequestState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoansFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val loansViewModel: LoansViewModel by viewModels(factoryProducer = { factory })
    private var binding: FragmentLoansBinding? = null
    private var disposable: CompositeDisposable? = CompositeDisposable()

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
        val adapter = LoansRecyclerAdapter()
        binding?.loansRecyclerView?.adapter = adapter

        disposable?.add(
            adapter.onClick
                .debounce(QUARTER_SECOND, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(::addLoanIdFragment)
        )
    }

    private fun renderData(state: RequestState<List<LoanDTO>>) {
        when (state) {
            is RequestState.Success -> {
                setSuccessState(state.value)
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

    private fun setSuccessState(loans: List<LoanDTO>) {
        binding?.progressBar?.isVisible = false
        binding?.loansRecyclerView?.isVisible = true
        (binding?.loansRecyclerView?.adapter as LoansRecyclerAdapter).updateLoansList(loans)
        checkStartNotification(loans)
    }

    private fun addLoanIdFragment(id: Int) {
        parentFragmentManager.beginTransaction()
            .add(R.id.container, LoanIdFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    private fun checkStartNotification(loans: List<LoanDTO>) {
        if (isWorkScheduled())
            WorkManager.getInstance(requireContext()).cancelAllWorkByTag(TAG_NOTIFY_WORK)

        val firstNotRegistered = loans.size - REGISTERED_LOAN
        if (firstNotRegistered > 1)
            startNotification(firstNotRegistered, loans[1].date.getDateText())
    }

    private fun startNotification(firstNotRegistered: Int, date: String) {
        val data = Data.Builder()
        data.putString(NOTIFY_DATE, date)
        data.putInt(NOTIFY_LOANS, firstNotRegistered)
        setPeriodicNotification(data)
    }

    private fun setPeriodicNotification(data: Data.Builder) {
        val instance = WorkManager.getInstance(requireContext())
        instance.enqueueUniquePeriodicWork(
            TAG_NOTIFY_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            getPeriodicWorkRequest(data)
        )
    }

    private fun getPeriodicWorkRequest(data: Data.Builder): PeriodicWorkRequest =
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            .let { constraints ->
                PeriodicWorkRequest.Builder(
                    NotificationWorker::class.java,
                    REPEAT_INTERVAL, TimeUnit.MINUTES,
                    FIX_INTERVAL, TimeUnit.MINUTES
                )
                    .addTag(TAG_NOTIFY_WORK)
                    .setInputData(data.build())
                    .setConstraints(constraints)
                    .build()
            }

    private fun isWorkScheduled(): Boolean =
        try {
            val statuses =
                WorkManager.getInstance(requireContext()).getWorkInfosByTag(TAG_NOTIFY_WORK)
            var running = false
            for (workInfo in statuses.get()) {
                val state = workInfo.state
                running = state == WorkInfo.State.RUNNING || state == WorkInfo.State.ENQUEUED
            }
            running
        } catch (e: ExecutionException) {
            e.printStackTrace()
            false
        } catch (e: InterruptedException) {
            e.printStackTrace()
            false
        }


    override fun onDetach() {
        disposable?.clear()
        disposable = null
        binding = null
        super.onDetach()
    }

    companion object {
        private const val FIX_INTERVAL = 25L
        private const val REPEAT_INTERVAL = 30L
        const val TAG = "LoansFragment"
        fun newInstance() = LoansFragment()
    }
}