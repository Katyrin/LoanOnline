package com.katyrin.loan_online.ui.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.katyrin.loan_online.databinding.ItemInfoBinding

class ViewPagerAdapter(
    private val texts: List<String>,
    private val images: List<Int>,
    private val onClick: () -> Unit
) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: ItemInfoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(position: Int) {
            itemBinding.infoText.text = texts[position]
            itemBinding.infoImage.setImageResource(images[position])
            itemBinding.tryButton.isVisible = ((itemCount - ONE_ITEM) == position)
            itemBinding.tryButton.setOnClickListener { onClick() }
        }
    }

    override fun getItemCount(): Int = texts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemInfoBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    private companion object {
        const val ONE_ITEM = 1
    }
}