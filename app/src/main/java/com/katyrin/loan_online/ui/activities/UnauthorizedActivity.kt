package com.katyrin.loan_online.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katyrin.loan_online.App
import com.katyrin.loan_online.R
import com.katyrin.loan_online.databinding.ActivityUnauthorizedBinding
import com.katyrin.loan_online.di.AppComponent
import com.katyrin.loan_online.ui.login.LoginFragment

class UnauthorizedActivity : AppCompatActivity(), OnAppCompatActivity {

    private var binding: ActivityUnauthorizedBinding? = null
    override var appComponent: AppComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as App).appComponent
        super.onCreate(savedInstanceState)
        binding = ActivityUnauthorizedBinding.inflate(layoutInflater)
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