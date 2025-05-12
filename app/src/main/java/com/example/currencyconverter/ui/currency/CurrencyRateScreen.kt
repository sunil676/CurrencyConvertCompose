package com.example.currencyconverter.ui.currency

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.currencyconverter.data.model.Rate
import com.example.currencyconverter.ui.common.ShowError
import com.example.currencyconverter.ui.common.ShowLoading
import com.example.currencyconverter.utils.UIState

@Composable
fun CurrencyRateScreen(viewModel: CurrencyViewModel = hiltViewModel()) {
    val currencyUIState by viewModel.currencyState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CurrencyRateTopBar()
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                InitiateCurrencyRateView(currencyUIState)
            }
        }
    )
}

@Composable
fun InitiateCurrencyRateView(uiState: UIState<Rate>) {
    when (uiState) {
        is UIState.Loading -> {
            ShowLoading()
        }

        is UIState.Success -> {
            CurrencyRateContent(
                rates = uiState.data,
                onConvert = { amount, currency ->
                    val baseCurrency = uiState.data.base
                    val rate = uiState.data.currencyRates?.get(currency)
                    val convertedAmount = amount / (rate ?:  1f)
                    val result = "$amount $baseCurrency = $convertedAmount $currency"
                },
            )
        }

        is UIState.Error -> {
            ShowError(text = uiState.message)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyRateTopBar() {
    TopAppBar(
        title = {Text(text = "Currency Rate")},
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
