package com.example.currencyconverter.ui.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.model.Rate
import com.example.currencyconverter.domain.usecases.remote.CurrencyUseCase
import com.example.currencyconverter.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CurrencyViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) :
    ViewModel() {

    private val _currencyState = MutableStateFlow<UIState<Rate>>(UIState.Loading)
    val currencyState: StateFlow<UIState<Rate>> get() = _currencyState

    init {
        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch {
            currencyUseCase.getCurrencies()
                .collect { state ->
                    when (state) {
                        is UIState.Loading -> {
                            _currencyState.value = UIState.Loading
                        }

                        is UIState.Success -> {
                            _currencyState.value = UIState.Success(state.data)
                        }

                        is UIState.Error -> {
                            _currencyState.value = UIState.Error(state.message)
                        }
                    }
                }
        }
    }
}