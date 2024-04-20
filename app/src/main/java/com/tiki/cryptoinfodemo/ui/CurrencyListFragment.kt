package com.tiki.cryptoinfodemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiki.cryptoinfodemo.common.launchAndCollectIn
import com.tiki.cryptoinfodemo.databinding.FragmentCurrencyListBinding
import com.tiki.cryptoinfodemo.ui.adapter.CurrencyListAdapter
import com.tiki.cryptoinfodemo.ui.viewmodel.CurrencyListViewModel
import com.tiki.cryptoinfodemo.ui.viewmodel.CurrencySharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyListFragment : Fragment() {
    private var _binding: FragmentCurrencyListBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel by activityViewModels<CurrencySharedViewModel>()
    private val viewModel by viewModel<CurrencyListViewModel>()
    private lateinit var currentListAdapter: CurrencyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        currentListAdapter = CurrencyListAdapter()
        binding.currenciesRecyclerView.apply {
            adapter = currentListAdapter
            layoutManager = LinearLayoutManager(context)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        sharedViewModel.currencyInfoList.launchAndCollectIn(viewLifecycleOwner) { currencyList ->
            viewModel.onSourceCurrencyInfoListChange(currencyList)
        }
        viewModel.currencyInfoUiList.launchAndCollectIn(viewLifecycleOwner) {
            currentListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}