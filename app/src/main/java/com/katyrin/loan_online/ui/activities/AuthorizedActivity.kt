package com.katyrin.loan_online.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.katyrin.loan_online.App
import com.katyrin.loan_online.R
import com.katyrin.loan_online.databinding.ActivityAuthorizedBinding
import com.katyrin.loan_online.di.AppComponent
import com.katyrin.loan_online.utils.findFragment

class AuthorizedActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener, OnAppCompatActivity {

    override var appComponent: AppComponent? = null
    private var binding: ActivityAuthorizedBinding? = null
    private var navPosition: BottomNavigationPosition = BottomNavigationPosition.HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as App).appComponent
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizedBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initBottomNavigation(savedInstanceState)
    }

    private fun initBottomNavigation(savedInstanceState: Bundle?) {
        binding?.bottomNavigation?.apply {
            setOnNavigationItemSelectedListener(this@AuthorizedActivity)
            if (savedInstanceState == null) selectedItemId = R.id.home
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean =
        switchFragment(supportFragmentManager.findFragment(
            findNavigationPositionById(item.itemId).also { navPosition = it }
        ))

    private fun switchFragment(fragment: Fragment): Boolean =
        if (fragment.isAdded) false
        else {
            removeAllStackFragments()
            detachFragment()
            checkIsDetachedFragment(fragment, navPosition.getTag())
            setTransitFragmentFade()
            supportFragmentManager.executePendingTransactions()
            true
        }

    private fun removeAllStackFragments() {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment !is BottomNavigationView)
                supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    private fun detachFragment() {
        supportFragmentManager.findFragmentById(R.id.container)?.also {
            supportFragmentManager.beginTransaction().detach(it).commit()
        }
    }

    private fun checkIsDetachedFragment(fragment: Fragment, tag: String) {
        if (fragment.isDetached)
            attachFragment(fragment)
        else
            replaceFragment(fragment, tag)
    }

    private fun attachFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .attach(fragment)
            .commit()
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, tag)
            .commit()
    }

    private fun setTransitFragmentFade() {
        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun onDestroy() {
        appComponent = null
        binding = null
        super.onDestroy()
    }
}