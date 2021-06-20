package com.katyrin.loan_online.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.katyrin.loan_online.R
import com.katyrin.loan_online.SessionManager
import com.katyrin.loan_online.databinding.FragmentDialogExitBinding
import com.katyrin.loan_online.ui.activities.MainActivity
import com.katyrin.loan_online.utils.TAG_NOTIFY_WORK
import com.katyrin.loan_online.viewmodel.ExitViewModel
import com.katyrin.loan_online.viewmodel.appstates.DeleteDBState
import javax.inject.Inject

class ExitDialog : BottomSheetDialogFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val exitViewModel: ExitViewModel by viewModels(factoryProducer = { factory })
    private var binding: FragmentDialogExitBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDialogExitBinding.inflate(inflater, container, false)
        .also { binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitViewModel.deleteDBState.observe(viewLifecycleOwner) { renderData(it) }

        initButtonsClick()
    }

    private fun initButtonsClick() {
        binding?.negativeButtonDialog?.setOnClickListener { dismiss() }
        binding?.positiveButtonDialog?.setOnClickListener {
            exitViewModel.clearData()
        }
    }

    private fun renderData(state: DeleteDBState) {
        when (state) {
            DeleteDBState.SUCCESS -> {
                WorkManager.getInstance(requireContext()).cancelAllWorkByTag(TAG_NOTIFY_WORK)
                clearPrefs()
                requireActivity().finish()
            }
            DeleteDBState.ERROR -> {
                Toast.makeText(requireContext(), R.string.data_not_deleted, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun clearPrefs() {
        SessionManager(requireContext()).apply {
            saveAuthToken(null, null, null)
            saveIsRegistered(false)
        }
    }

    override fun onDetach() {
        binding = null
        super.onDetach()
    }

    companion object {
        private const val EXIT_DIALOG = "EXIT_DIALOG"

        fun newInstance(fragmentManager: FragmentManager) = ExitDialog().apply {
            show(fragmentManager, EXIT_DIALOG)
        }
    }
}