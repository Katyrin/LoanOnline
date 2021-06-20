package com.katyrin.loan_online.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katyrin.loan_online.App
import com.katyrin.loan_online.SessionManager
import com.katyrin.loan_online.databinding.ActivitySplashBinding
import com.katyrin.loan_online.utils.EXTRA_IS_AUTHORIZED
import com.katyrin.loan_online.utils.SPLASH_ACTIVITY_ANIMATION_TIME
import com.katyrin.loan_online.utils.setRotateImage
import com.katyrin.loan_online.viewmodel.SplashViewModel
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val splashViewModel: SplashViewModel by viewModels(factoryProducer = { factory })
    private var binding: ActivitySplashBinding? = null
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        splashViewModel.tokenState.observe(this) { renderData(it) }
        startAnimation()
    }

    private fun startAnimation() {
        binding?.imageView?.setRotateImage()
        handler.postDelayed(
            { splashViewModel.checkToken(SessionManager(this).fetchAuthToken()) },
            SPLASH_ACTIVITY_ANIMATION_TIME
        )
    }

    private fun renderData(state: Boolean) {
        val intent = Intent(this, MainActivity::class.java)
        when (state) {
            false -> intent.putExtra(EXTRA_IS_AUTHORIZED, false)
            true -> intent.putExtra(EXTRA_IS_AUTHORIZED, true)
        }
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        binding = null
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}