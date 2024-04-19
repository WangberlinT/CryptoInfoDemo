package com.tiki.cryptoinfodemo.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tiki.cryptoinfodemo.R
import com.tiki.cryptoinfodemo.common.launchAndCollectIn
import com.tiki.cryptoinfodemo.databinding.ActivityMainBinding
import com.tiki.cryptoinfodemo.ui.viewmodel.CurrencySharedViewModel
import com.tiki.cryptoinfodemo.ui.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<MainActivityViewModel>()
    private val sharedViewModel by viewModel<CurrencySharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupListeners()
        setupObservers()
    }


    private fun setupObservers() {
        viewModel.currencyInfo.launchAndCollectIn(this) {
            sharedViewModel.onCurrencyInfoSourceChange(it)
        }
    }

    private fun setupListeners() {
        binding.remove.setOnClickListener { viewModel.onRemoveClick() }
        binding.insert.setOnClickListener { viewModel.onInsertClick() }
        binding.crypto.setOnClickListener { viewModel.onCryptoCurrencyClick() }
        binding.fiat.setOnClickListener { viewModel.onFiatCurrencyClick() }
        binding.all.setOnClickListener { viewModel.onAllCurrencyClick() }
    }

}