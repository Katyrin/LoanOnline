package com.katyrin.loan_online.ui.loans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.katyrin.loan_online.R
import com.katyrin.loan_online.data.model.LoanDTO
import com.katyrin.loan_online.data.model.LoanState
import com.katyrin.loan_online.databinding.ItemLoanBinding

class LoansRecyclerAdapter(private val loans: List<LoanDTO>) :
    RecyclerView.Adapter<LoansRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: ItemLoanBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(loan: LoanDTO) {
            itemBinding.amountTextView.text = loan.amount.toString()
            itemBinding.loanStateImage.setImageResource(getImageId(loan))
            LoanState.APPROVED
        }

        private fun getImageId(loan: LoanDTO): Int =
            when(loan.state) {
                LoanState.APPROVED -> R.drawable.ic_approved
                LoanState.REGISTERED -> R.drawable.ic_wait_money
                LoanState.REJECTED -> R.drawable.ic_rejected
                else -> R.drawable.ic_unknow_state
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemLoanBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(loans[position])
    }

    override fun getItemCount(): Int = loans.size
}