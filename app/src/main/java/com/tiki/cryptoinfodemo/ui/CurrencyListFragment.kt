package com.tiki.cryptoinfodemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiki.cryptoinfodemo.common.launchAndCollectIn
import com.tiki.cryptoinfodemo.databinding.FragmentCurrencyListBinding
import com.tiki.cryptoinfodemo.ui.adapter.CurrencyListAdapter
import com.tiki.cryptoinfodemo.ui.adapter.SearchListAdapter
import com.tiki.cryptoinfodemo.ui.viewmodel.CurrencyListViewModel
import com.tiki.cryptoinfodemo.ui.viewmodel.CurrencySharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyListFragment : Fragment() {
    private var _binding: FragmentCurrencyListBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel by activityViewModels<CurrencySharedViewModel>()
    private val viewModel by viewModel<CurrencyListViewModel>()
    private lateinit var currentListAdapter: CurrencyListAdapter
    private lateinit var searchListAdapter: SearchListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        currentListAdapter = CurrencyListAdapter()
        searchListAdapter = SearchListAdapter()
        binding.currenciesRecyclerView.apply {
            adapter = currentListAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.searchResult.apply {
            adapter = searchListAdapter
            layoutManager = LinearLayoutManager(context)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.searchView.editText.addTextChangedListener { editable ->
            viewModel.onSearchQueryChange(editable.toString())
        }
        binding.searchView.toolbar.setNavigationOnClickListener {
            viewModel.onSearchFinished()
            binding.searchView.hide()
        }
    }

    private fun setupObservers() {
        sharedViewModel.currencyInfoList.launchAndCollectIn(viewLifecycleOwner) { currencyList ->
            viewModel.onSourceCurrencyInfoListChange(currencyList)
        }
        viewModel.currencyInfoUiList.launchAndCollectIn(viewLifecycleOwner) {
            currentListAdapter.submitList(it)
        }
        viewModel.searchResult.launchAndCollectIn(viewLifecycleOwner) {
            searchListAdapter.submitList(it)
            binding.emptyView.root.isVisible = it.isEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}