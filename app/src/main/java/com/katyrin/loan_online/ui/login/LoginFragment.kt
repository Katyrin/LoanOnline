package com.katyrin.loan_online.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.katyrin.loan_online.R
import com.katyrin.loan_online.databinding.FragmentLoginBinding
import com.katyrin.loan_online.utils.afterTextChanged
import com.katyrin.loan_online.viewmodel.login.*
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val loginViewModel: LoginViewModel by viewModels(factoryProducer = { factory })
    private var binding: FragmentLoginBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as LoginActivity).appComponent?.inject(this)
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

        initViews()
    }

    private fun initViews() {
        binding?.username?.afterTextChanged {
            loginViewModel.loginDataChanged(
                binding?.username?.text.toString(),
                binding?.password?.text.toString()
            )
        }

        binding?.password?.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    binding?.username?.text.toString(),
                    binding?.password?.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.registration(
                            binding?.username?.text.toString(),
                            binding?.password?.text.toString()
                        )
                }
                false
            }

            binding?.login?.setOnClickListener {
                binding?.loading?.isVisible = true
                loginViewModel.registration(
                    binding?.username?.text.toString(),
                    binding?.password?.text.toString()
                )
            }
        }
    }

    private fun handleLoginState(state: LoginFormState) {
        when(state) {
            is LoginFormState.ErrorUserName -> {
                binding?.username?.error = getString(R.string.invalid_username)
            }
            is LoginFormState.ErrorPassword -> {
                binding?.password?.error = getString(R.string.invalid_password)
            }
            is LoginFormState.Success -> {
                binding?.login?.isEnabled = true
            }
        }
    }

    private fun handleLoginResult(state: LoginResult) {
        binding?.loading?.isVisible = false
        when(state) {
            is LoginResult.Success -> {
                updateUiWithUser(state.loggedInUserView)
            }
            is LoginResult.Error -> {
                showLoginFailed(R.string.login_failed)
            }
        }
    }


    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            requireContext(),
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        binding = null
        super.onDetach()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}