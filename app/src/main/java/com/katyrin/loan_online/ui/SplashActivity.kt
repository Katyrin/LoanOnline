package com.katyrin.loan_online.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.katyrin.loan_online.databinding.ActivitySplashBinding
import com.katyrin.loan_online.ui.login.LoginActivity
import com.katyrin.loan_online.utils.setRotateImage

class SplashActivity : AppCompatActivity() {

    private var binding: ActivitySplashBinding? = null
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        startAnimation()
    }

    private fun startAnimation() {
        binding?.imageView?.setRotateImage()
        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, 1000)
    }

    override fun onDestroy() {
        binding = null
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}