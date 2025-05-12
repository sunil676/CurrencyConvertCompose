package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.currencyconverter.domain.network.ConnectivityService
import com.example.currencyconverter.ui.currency.CurrencyRateScreen
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityService: ConnectivityService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterTheme {
                CurrencyRateScreen()

                val connectivityStatusSnackbarHostState = SnackbarHostState()
                lifecycleScope.launch {
                    connectivityService.isNetworkAvailable()
                        .collectLatest { isConnectionAvailable ->
                            if (!isConnectionAvailable) { // 3.
                                connectivityStatusSnackbarHostState.showSnackbar(
                                    message = "No internet, please wait"
                                )
                            } else {
                                connectivityStatusSnackbarHostState
                                    .currentSnackbarData?.dismiss()
                            }
                        }
                }

                SnackbarHost(
                    modifier = Modifier.safeDrawingPadding(),
                    hostState = connectivityStatusSnackbarHostState,
                ) { data ->
                    Surface(
                        shadowElevation = 6.dp,
                        shape = MaterialTheme.shapes.large,
                    ) {
                        Row(
                            modifier =
                            Modifier.padding(16.dp),
                            verticalAlignment =
                            Alignment.CenterVertically,
                            horizontalArrangement =
                            Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                painter =
                                painterResource(
                                    id = android.R.drawable.ic_dialog_info
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                            )
                            Text(
                                text = data.visuals.message,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CurrencyConverterTheme {
            CurrencyRateScreen()
        }
    }
}