package com.katyrin.loan_online.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.katyrin.loan_online.R
import com.katyrin.loan_online.databinding.FragmentInfoViewPagerBinding


class InfoViewPagerFragment : Fragment() {

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
        binding?.viewPager?.adapter = ViewPagerAdapter(textList, imageList)
        binding?.indicator?.setViewPager(binding?.viewPager)
    }

    private fun addToList(text: String, image: Int) {
        textList.add(text)
        imageList.add(image)
    }


    private fun postToList() {
        for (i in 1..5) {
            addToList("$userName $i", R.drawable.ic_money)
        }
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