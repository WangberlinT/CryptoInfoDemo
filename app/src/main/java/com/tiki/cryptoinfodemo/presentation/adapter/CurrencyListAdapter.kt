package com.tiki.cryptoinfodemo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tiki.cryptoinfodemo.databinding.ItemCurrencyBinding
import com.tiki.cryptoinfodemo.domain.model.CurrencyItemUi

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
            binding.name.text = item.name
            binding.firstChar.text = item.iconText.toString()
            binding.next.isVisible = item.isRightArrowVisible
            binding.code.isVisible = item.isCodeVisible
            binding.code.text = item.code
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