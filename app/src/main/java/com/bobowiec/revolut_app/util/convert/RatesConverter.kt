package com.bobowiec.revolut_app.util.convert

import com.bobowiec.revolut_app.data.model.Currency
import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.extensions.multiply
import java.math.BigDecimal

interface RefreshRatesListener {
  fun refreshAllRates()
  fun refreshExchangeRates()
}

class RatesConverter(
    private val refreshRatesListener: RefreshRatesListener,
    var baseRateValue: BigDecimal = Rate.BASE_RATE_DEFAULT_VALUE,
    var baseRateSymbol: String = Currency.EUR.symbol) {

  private var previousRates: List<Rate> = ArrayList()

  fun convert(rates: List<Rate>) {
    if (baseRateChanged(rates)) {
      convertForNewBaseRate(rates)
    } else {
      convertForPreviousBaseRate(rates)
    }
    previousRates = rates
  }

  private fun baseRateChanged(rates: List<Rate>): Boolean =
      rates.isNotEmpty() && rates[0].symbol != baseRateSymbol

  private fun convertForNewBaseRate(rates: List<Rate>) {
    baseRateSymbol = rates[0].symbol
    baseRateValue = getBaseRatePreviousValue()
    rates[0].value = baseRateValue
    rates.subList(1, rates.size).map { it.multiply(baseRateValue) }

    refreshRatesListener.refreshAllRates()
  }

  private fun convertForPreviousBaseRate(rates: List<Rate>) {
    rates.first().value = baseRateValue
    rates.subList(1, rates.size).map { it.multiply(baseRateValue) }

    refreshRatesListener.refreshExchangeRates()
  }

  private fun getBaseRatePreviousValue() = previousRates.firstOrNull {
    it.symbol == baseRateSymbol
  }?.value ?: Rate.BASE_RATE_DEFAULT_VALUE

}