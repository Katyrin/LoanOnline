package com.katyrin.loan_online.ui.login

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
import com.katyrin.loan_online.databinding.FragmentLoginBinding
import com.katyrin.loan_online.ui.activities.MainActivity
import com.katyrin.loan_online.ui.info.InfoViewPagerFragment
import com.katyrin.loan_online.ui.main.MainFragment
import com.katyrin.loan_online.utils.afterTextChanged
import com.katyrin.loan_online.utils.hideKeyboard
import com.katyrin.loan_online.utils.showErrorMessage
import com.katyrin.loan_online.utils.toast
import com.katyrin.loan_online.viewmodel.LoginViewModel
import com.katyrin.loan_online.viewmodel.appstates.LoginFormState
import com.katyrin.loan_online.viewmodel.appstates.RequestState
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val loginViewModel: LoginViewModel by viewModels(factoryProducer = { factory })
    private var binding: FragmentLoginBinding? = null
    private val _textInput = BehaviorSubject.create<Pair<String, String>>()
    private val textInput = _textInput.toFlowable(BackpressureStrategy.LATEST)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.loginFormState.observe(viewLifecycleOwner) { handleLoginState(it) }
        loginViewModel.requestState.observe(viewLifecycleOwner) { handleLoginResult(it) }
        loginViewModel.registeredState.observe(viewLifecycleOwner) { setIsRegisteredState(it) }
        loginViewModel.saveDataState.observe(viewLifecycleOwner) { openFragment(it) }

        loginViewModel.subscribeLoginDataChanged(textInput)
        initEditTextViews()
        initButtons()
    }

    private fun initEditTextViews() {
        binding?.username?.afterTextChanged { onNextTextInput() }
        binding?.password?.apply {
            afterTextChanged { onNextTextInput() }

            setOnEditorActionListener { _, _, _ ->
                requireActivity().hideKeyboard()
                startRegistration()
                false
            }
        }
    }

    private fun initButtons() {
        binding?.registrationButton?.setOnClickListener { startRegistration() }
        binding?.loginButton?.setOnClickListener { startLogin() }
        binding?.registeredButton?.setOnClickListener {
            registrationState()
            loginViewModel.saveIsRegistered(true)
        }
        binding?.noRegisteredButton?.setOnClickListener {
            loginState()
            loginViewModel.saveIsRegistered(false)
        }
    }

    private fun registrationState() {
        binding?.registeredButton?.isVisible = false
        binding?.registrationButton?.isVisible = false
        binding?.noRegisteredButton?.isVisible = true
        binding?.loginButton?.isVisible = true
    }

    private fun loginState() {
        binding?.registeredButton?.isVisible = true
        binding?.registrationButton?.isVisible = true
        binding?.noRegisteredButton?.isVisible = false
        binding?.loginButton?.isVisible = false
    }

    private fun onNextTextInput() {
        _textInput.onNext(binding?.username?.text.toString() to binding?.password?.text.toString())
    }

    private fun startRegistration() {
        if (binding?.registrationButton?.isEnabled == true) {
            loginViewModel.registration(
                binding?.username?.text.toString(),
                binding?.password?.text.toString()
            )
        }
    }

    private fun startLogin() {
        if (binding?.loginButton?.isEnabled == true) {
            loginViewModel.login(
                binding?.username?.text.toString(),
                binding?.password?.text.toString()
            )
        }
    }

    private fun handleLoginState(state: LoginFormState) {
        when (state) {
            is LoginFormState.ErrorUserName -> {
                binding?.username?.error = getString(R.string.invalid_username)
            }
            is LoginFormState.ErrorPassword -> {
                binding?.password?.error = getString(R.string.invalid_password)
            }
            is LoginFormState.Success -> {
                binding?.registrationButton?.isEnabled = true
                binding?.loginButton?.isEnabled = true
            }
        }
    }

    private fun handleLoginResult(state: RequestState<String>) {
        when (state) {
            is RequestState.Success -> {
                visibleScreenState()
                setSuccessState(state.value)
            }
            is RequestState.ServerError -> {
                visibleScreenState()
                requireContext().toast(getString(R.string.server_error))
            }
            is RequestState.ClientError -> {
                visibleScreenState()
                requireContext().showErrorMessage(state.code)
            }
            is RequestState.Loading -> {
                loadingState()
            }
        }
    }

    private fun loadingState() {
        binding?.apply {
            usernameLayout.isVisible = false
            passwordLayout.isVisible = false
            registeredButton.isVisible = false
            noRegisteredButton.isVisible = false
            registrationButton.isVisible = false
            loginButton.isVisible = false
            progressBar.isVisible = true
        }
    }

    private fun visibleScreenState() {
        binding?.apply {
            usernameLayout.isVisible = true
            passwordLayout.isVisible = true
            progressBar.isVisible = false
        }

        loginViewModel.getIsRegistered()
    }

    private fun setIsRegisteredState(isRegistered: Boolean) {
        if (isRegistered) registrationState()
        else loginState()
    }

    private fun setSuccessState(token: String) {
        updateUiWithUser()
        loginViewModel.saveData(token)
    }

    private fun openFragment(isRegistered: Boolean) {
        if (isRegistered) replaceMainFragment()
        else replaceInfoViewPagerFragment()
    }

    private fun replaceMainFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MainFragment.newInstance())
            .commitNow()
    }

    private fun replaceInfoViewPagerFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.main_container,
                InfoViewPagerFragment.newInstance(binding?.username?.text.toString())
            )
            .commitNow()
    }

    private fun updateUiWithUser() {
        val welcome =
            getString(R.string.welcome) + " ${binding?.username?.text.toString()}"
        requireContext().toast(welcome)
    }

    override fun onDetach() {
        binding = null
        super.onDetach()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}