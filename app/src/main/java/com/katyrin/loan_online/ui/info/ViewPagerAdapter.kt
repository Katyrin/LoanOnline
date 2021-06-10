package com.katyrin.loan_online.ui.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.katyrin.loan_online.databinding.FragmentInfoBinding
import com.katyrin.loan_online.utils.ONE_ITEM

class ViewPagerAdapter(
    private val texts: List<String>,
    private var images: List<Int>
) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: FragmentInfoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(position: Int) {
            itemBinding.infoText.text = texts[position]
            itemBinding.infoImage.setImageResource(images[position])
            itemBinding.tryButton.isVisible = ((itemCount - ONE_ITEM) == position)
        }
    }

    override fun getItemCount(): Int = texts.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = FragmentInfoBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }


}