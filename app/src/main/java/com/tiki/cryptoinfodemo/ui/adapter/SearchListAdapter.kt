package com.tiki.cryptoinfodemo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tiki.cryptoinfodemo.databinding.ItemSearchCurrencyBinding
import com.tiki.cryptoinfodemo.domain.CurrencyItemUi

class SearchListAdapter
    : ListAdapter<CurrencyItemUi, SearchListAdapter.SearchListListVH>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<CurrencyItemUi>() {
        override fun areItemsTheSame(oldItem: CurrencyItemUi, newItem: CurrencyItemUi): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CurrencyItemUi, newItem: CurrencyItemUi): Boolean {
            return oldItem == newItem
        }

    }

    class SearchListListVH(private val binding: ItemSearchCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CurrencyItemUi) {
            binding.name.text = item.name
            binding.firstChar.text = item.iconText.toString()
            binding.next.isVisible = item.isRightArrowVisible
            binding.code.isVisible = item.isCodeVisible
            binding.code.text = item.code
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListListVH {
        val binding =
            ItemSearchCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchListListVH(binding)
    }

    override fun onBindViewHolder(holder: SearchListListVH, position: Int) {
        holder.bind(getItem(position))
    }
}
