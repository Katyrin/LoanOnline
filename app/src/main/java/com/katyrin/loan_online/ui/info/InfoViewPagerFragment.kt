package com.katyrin.loan_online.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.katyrin.loan_online.R
import com.katyrin.loan_online.databinding.FragmentInfoViewPagerBinding
import com.katyrin.loan_online.ui.activities.BackButtonListener
import com.katyrin.loan_online.ui.loanconditions.LoanConditionsFragment


class InfoViewPagerFragment : Fragment(), BackButtonListener {

    private var binding: FragmentInfoViewPagerBinding? = null
    private var userName: String? = null
    private val textList: MutableList<String> = mutableListOf()
    private val imageList: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userName = it.getString(USER_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentInfoViewPagerBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postToList()
        binding?.viewPager?.adapter =
            ViewPagerAdapter(textList, imageList) { replaceLoanConditionsFragment() }
        binding?.indicator?.setViewPager(binding?.viewPager)
    }

    private fun replaceLoanConditionsFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, LoanConditionsFragment.newInstance())
            .commitNow()
    }

    private fun addToList(text: String, image: Int) {
        textList.add(text)
        imageList.add(image)
    }


    private fun postToList() {
        val textOne =
            getString(R.string.welcome) + " $userName\n" + getString(R.string.view_pager_text_one)
        addToList(textOne, R.drawable.ic_coins_planet)
        addToList(getString(R.string.view_pager_text_two), R.drawable.ic_offer)
        addToList(getString(R.string.view_pager_text_three), R.drawable.ic_form)
        addToList(getString(R.string.view_pager_text_four), R.drawable.ic_states)
        addToList(getString(R.string.view_pager_text_five), R.drawable.ic_like)
    }

    override fun onDetach() {
        binding = null
        userName = null
        super.onDetach()
    }

    companion object {
        private const val USER_NAME = "USER_NAME"
        fun newInstance(userName: String?) =
            InfoViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_NAME, userName)
                }
            }
    }
}