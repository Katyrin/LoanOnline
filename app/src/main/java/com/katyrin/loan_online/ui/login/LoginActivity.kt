package com.katyrin.loan_online.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katyrin.loan_online.App
import com.katyrin.loan_online.R
import com.katyrin.loan_online.databinding.ActivityLoginBinding
import com.katyrin.loan_online.di.AppComponent

class LoginActivity : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null
    var appComponent: AppComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as App).appComponent
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if (savedInstanceState == null) replaceLoginFragment()
    }

    private fun replaceLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment.newInstance())
            .commitNow()
    }

    override fun onDestroy() {
        appComponent = null
        binding = null
        super.onDestroy()
    }
}