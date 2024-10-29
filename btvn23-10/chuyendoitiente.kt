
package com.example.lptrnhngdngchuynitint

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat
import com.example.lptrnhngdngchuynitint.R


class MainActivity : AppCompatActivity() {

    private lateinit var sourceAmountEditText: EditText
    private lateinit var targetAmountEditText: EditText
    private lateinit var sourceCurrencySpinner: Spinner
    private lateinit var targetCurrencySpinner: Spinner

    
    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 1.2,
        "VND" to 23000.0,
        "JPY" to 110.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sourceAmountEditText = findViewById(R.id.sourceAmountEditText)
        targetAmountEditText = findViewById(R.id.targetAmountEditText)
        sourceCurrencySpinner = findViewById(R.id.sourceCurrencySpinner)
        targetCurrencySpinner = findViewById(R.id.targetCurrencySpinner)

        
        val currencyOptions = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sourceCurrencySpinner.adapter = adapter
        targetCurrencySpinner.adapter = adapter

        
        sourceAmountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertCurrency()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

       
        sourceCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        targetCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun convertCurrency() {
                val amountStr = sourceAmountEditText.text.toString()
        val amount = if (amountStr.isNotEmpty()) amountStr.toDouble() else 0.0

        
        val sourceCurrency = sourceCurrencySpinner.selectedItem.toString()
        val targetCurrency = targetCurrencySpinner.selectedItem.toString()

        
        val sourceRate = exchangeRates[sourceCurrency] ?: 1.0
        val targetRate = exchangeRates[targetCurrency] ?: 1.0

        
        val convertedAmount = (amount / sourceRate) * targetRate

       
        val decimalFormat = DecimalFormat("#,###.##")
        targetAmountEditText.setText(decimalFormat.format(convertedAmount))
    }
}
