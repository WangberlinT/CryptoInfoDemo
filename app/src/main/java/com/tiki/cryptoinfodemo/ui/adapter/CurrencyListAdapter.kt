package com.tiki.cryptoinfodemo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tiki.cryptoinfodemo.databinding.ItemCurrencyBinding
import com.tiki.cryptoinfodemo.domain.CurrencyItemUi

class CurrencyListAdapter :
    ListAdapter<CurrencyItemUi, CurrencyListAdapter.CurrencyListVH>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<CurrencyItemUi>() {
        override fun areItemsTheSame(oldItem: CurrencyItemUi, newItem: CurrencyItemUi): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CurrencyItemUi, newItem: CurrencyItemUi): Boolean {
            return oldItem == newItem
        }

    }

    class CurrencyListVH(private val binding: ItemCurrencyBinding) : ViewHolder(binding.root) {

        fun bind(item: CurrencyItemUi) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListVH {
        val binding =
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyListVH(binding)
    }

    override fun onBindViewHolder(holder: CurrencyListVH, position: Int) {
        holder.bind(getItem(position))
    }
}