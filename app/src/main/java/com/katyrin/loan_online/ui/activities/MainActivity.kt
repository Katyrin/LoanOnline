package com.katyrin.loan_online.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katyrin.loan_online.App
import com.katyrin.loan_online.R
import com.katyrin.loan_online.databinding.ActivityMainBinding
import com.katyrin.loan_online.di.AppComponent
import com.katyrin.loan_online.ui.login.LoginFragment
import com.katyrin.loan_online.ui.main.MainFragment
import com.katyrin.loan_online.utils.EXTRA_IS_AUTHORIZED

class MainActivity : AppCompatActivity() {

    var appComponent: AppComponent? = null
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as App).appComponent
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        if (savedInstanceState == null) firstStart()
    }

    private fun firstStart() {
        if (intent.getBooleanExtra(EXTRA_IS_AUTHORIZED, false))
            replaceMainFragment()
        else
            replaceLoginFragment()
    }

    private fun replaceMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MainFragment.newInstance())
            .commitNow()
    }

    private fun replaceLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, LoginFragment.newInstance())
            .commitNow()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener) {
                replaceMainFragment()
                return
            }
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        appComponent = null
        binding = null
        super.onDestroy()
    }
}