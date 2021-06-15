package com.katyrin.loan_online.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.katyrin.loan_online.R
import com.katyrin.loan_online.data.api.interceptor.SessionManager
import com.katyrin.loan_online.databinding.FragmentLoginBinding
import com.katyrin.loan_online.ui.activities.AuthorizedActivity
import com.katyrin.loan_online.ui.activities.OnAppCompatActivity
import com.katyrin.loan_online.ui.info.InfoViewPagerFragment
import com.katyrin.loan_online.utils.afterTextChanged
import com.katyrin.loan_online.viewmodel.login.LoginFormState
import com.katyrin.loan_online.viewmodel.login.LoginResult
import com.katyrin.loan_online.viewmodel.login.LoginViewModel
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
        (activity as OnAppCompatActivity).appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.loginFormState.observe(viewLifecycleOwner) { handleLoginState(it) }
        loginViewModel.loginResult.observe(viewLifecycleOwner) { handleLoginResult(it) }

        loginViewModel.subscribeLoginDataChanged(textInput)
        initEditTextViews()
        initButtons()
    }

    private fun initEditTextViews() {
        binding?.username?.afterTextChanged { onNextTextInput() }
        binding?.password?.apply {
            afterTextChanged { onNextTextInput() }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> startRegistration()
                }
                false
            }
        }
    }

    private fun initButtons() {
        binding?.registrationButton?.setOnClickListener { startRegistration() }
        binding?.loginButton?.setOnClickListener { startLogin() }
        binding?.registeredButton?.setOnClickListener {
            binding?.registeredButton?.isVisible = false
            binding?.noRegisteredButton?.isVisible = true
        }
        binding?.noRegisteredButton?.setOnClickListener {
            binding?.registeredButton?.isVisible = true
            binding?.noRegisteredButton?.isVisible = false
        }
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

    private fun handleLoginResult(state: LoginResult) {
        binding?.loading?.isVisible = false
        when (state) {
            is LoginResult.SuccessRegistration -> {
                successRegistrationRenderData(state.token)
            }
            is LoginResult.SuccessLogin -> {
                successLoginRenderData(state.token)
            }
            is LoginResult.Error -> {
                showLoginFailed()
            }
        }
    }

    private fun successRegistrationRenderData(token: String?) {
        saveData(token, binding?.username?.text.toString(), binding?.password?.text.toString())
        updateUiWithUser()
        replaceInfoViewPagerFragment()
    }

    private fun successLoginRenderData(token: String?) {
        saveData(token, binding?.username?.text.toString(), binding?.password?.text.toString())
        updateUiWithUser()
        startActivity(Intent(requireContext(), AuthorizedActivity::class.java))
        requireActivity().finish()
    }

    private fun saveData(token: String?, name: String?, password: String?) {
        SessionManager(requireContext()).saveAuthToken(
            token.toString(),
            name.toString(),
            password.toString()
        )
    }

    private fun replaceInfoViewPagerFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, InfoViewPagerFragment.newInstance("Roman"))
            .commitNow()
    }

    private fun updateUiWithUser() {
        val welcome =
            getString(R.string.welcome) + " ${SessionManager(requireContext()).fetchUserName()}"
        Toast.makeText(requireContext(), welcome, Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed() {
        Toast.makeText(requireContext(), R.string.login_failed, Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        binding = null
        super.onDetach()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}