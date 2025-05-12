package com.example.currencyconverter.ui.currency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.data.model.Rate

@Composable
fun CurrencyRateContent(
    rates: Rate,
    onConvert: (Float, String) -> Unit
) {

    var selectedCurrency by remember { mutableStateOf("USD") }
    var amountInput by remember { mutableStateOf(TextFieldValue("")) }
    val currencyList = rates.currencyRates?.keys?.toList()

    Column(modifier = Modifier.padding(16.dp)) {

        TextField(
            value = amountInput, onValueChange =
            { newValue ->
                // Update the amount input when the user types
                amountInput = newValue
            },
            label = { Text("Enter Amount") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("amountInput") // Test tag for UI testing
        )

        Spacer(modifier = Modifier.height(16.dp))

        // dropdown menu for currency selection
        CurrencyDropdownMenu(
            currencyList = currencyList ?: emptyList(),
            selectedCurrency = selectedCurrency,
            onCurrencySelected = { selected ->
                // Update the selected currency when the user selects from the dropdown
                selectedCurrency = selected
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display the selected currency and amount

        ConversionCurrencies(
            rates = rates.currencyRates,
            selectedCurrency = selectedCurrency,
            amountInput = amountInput.text
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropdownMenu(
    currencyList: List<String>?,
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit
) {
    // Implement the dropdown menu for currency selection
    // Use a LazyColumn or similar component to display the list of currencies
    // Call onCurrencySelected when a currency is selected

    var expanded by remember { mutableStateOf(false) } // State to control dropdown visibility

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedCurrency,
            onValueChange = {},
            label = { Text("Select Currency") },
            trailingIcon = { TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            currencyList?.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(text = currency) },
                    onClick = {
                        expanded = false
                        onCurrencySelected(currency)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            } ?: run {
                // "No currencies available"
                DropdownMenuItem(
                    text = { Text(text = "No currency available") },
                    onClick = { expanded = false })
            }
        }
    }
}


@Composable
fun ConversionCurrencies(
    rates: Map<String, Float>?,
    selectedCurrency: String,
    amountInput: String
) {
    // Display the selected currency and amount
    val amount = amountInput.toFloatOrNull() ?: 0f // Convert the input to a float
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("conversionResult") // Test tag for UI testing
    ) {
        item {
            val convertedAmount = rates?.get(selectedCurrency)?.let { rate ->
                amount * rate
            } ?: 0f // Calculate the converted amount

            ConversionResult(
                convertedAmount = convertedAmount,
                selectedCurrency = selectedCurrency,
                baseCurrency = "USD" // Assuming USD is the base currency
            )
        }

    }
}

@Composable
fun ConversionResult(
    convertedAmount: Float,
    selectedCurrency: String,
    baseCurrency: String
) {
    // Display the conversion result
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Converted from $baseCurrency to $selectedCurrency: ",
            modifier = Modifier
                .testTag("conversionResultLabel") // Test tag for UI testing
        )

        Text(
            text = String.format("%.2f", convertedAmount),
            modifier = Modifier
                .testTag("conversionResultValue") // Test tag for UI testing
        )

    }
}