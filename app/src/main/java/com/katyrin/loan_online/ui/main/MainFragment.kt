package com.katyrin.loan_online.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.katyrin.loan_online.R
import com.katyrin.loan_online.databinding.FragmentMainBinding
import com.katyrin.loan_online.utils.findFragment

class MainFragment : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var binding: FragmentMainBinding? = null
    private var navPosition: BottomNavigationPosition = BottomNavigationPosition.HOME

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation(savedInstanceState)
    }

    private fun initBottomNavigation(savedInstanceState: Bundle?) {
        binding?.bottomNavigation?.apply {
            setOnNavigationItemSelectedListener(this@MainFragment)
            if (savedInstanceState == null) selectedItemId = R.id.home
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean =
        switchFragment(childFragmentManager.findFragment(
            findNavigationPositionById(item.itemId).also { navPosition = it }
        ))

    private fun switchFragment(fragment: Fragment): Boolean =
        if (fragment.isAdded) false
        else {
            removeAllStackFragments()
            detachFragment()
            checkIsDetachedFragment(fragment, navPosition.getTag())
            setTransitFragmentFade()
            childFragmentManager.executePendingTransactions()
            true
        }

    private fun removeAllStackFragments() {
        for (fragment in childFragmentManager.fragments) {
            if (fragment !is BottomNavigationView)
                childFragmentManager.beginTransaction().remove(fragment)
                    .commit()
        }
    }

    private fun detachFragment() {
        childFragmentManager.findFragmentById(R.id.main_container)?.also {
            childFragmentManager.beginTransaction().detach(it).commit()
        }
    }

    private fun checkIsDetachedFragment(fragment: Fragment, tag: String) {
        if (fragment.isDetached)
            attachFragment(fragment)
        else
            replaceFragment(fragment, tag)
    }

    private fun attachFragment(fragment: Fragment) {
        childFragmentManager
            .beginTransaction()
            .attach(fragment)
            .commit()
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, tag)
            .commit()
    }

    private fun setTransitFragmentFade() {
        childFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}