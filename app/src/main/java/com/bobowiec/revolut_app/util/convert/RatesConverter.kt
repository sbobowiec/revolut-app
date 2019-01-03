package com.bobowiec.revolut_app.util.convert

import com.bobowiec.revolut_app.data.model.Currency
import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.extensions.multiply
import java.math.BigDecimal

class RatesConverter(
    private var baseRateValue: BigDecimal = Rate.BASE_RATE_DEFAULT_VALUE,
    private var baseRateSymbol: String = Currency.EUR.symbol) {

  private var previousRates: List<Rate> = ArrayList()

  fun convert(rates: List<Rate>): List<Rate> {
    val result = if (baseRateChanged(rates)) {
      convertForNewBaseRate(rates)
    } else {
      convertForPreviousBaseRate(rates)
    }
    previousRates = result

    return result
  }

  fun updateBaseRate(newBaseRateValue: BigDecimal) {
    baseRateValue = newBaseRateValue.setScale(
        Rate.BASE_RATE_DECIMAL_PLACES,
        BigDecimal.ROUND_HALF_UP
    )
  }

  fun baseRateChanged(rates: List<Rate>): Boolean =
      rates.isNotEmpty() && rates[0].symbol != baseRateSymbol

  private fun convertForNewBaseRate(rates: List<Rate>): List<Rate> {
    val result = copyRates(rates)

    baseRateSymbol = result[0].symbol
    baseRateValue = getBaseRatePreviousValue()
    result[0].value = baseRateValue
    result.subList(1, result.size).map { it.multiply(baseRateValue) }

    return result.toList()
  }

  private fun convertForPreviousBaseRate(rates: List<Rate>): List<Rate> {
    val result = copyRates(rates)

    result.first().value = baseRateValue
    result.subList(1, result.size).map { it.multiply(baseRateValue) }

    return result
  }

  private fun copyRates(rates: List<Rate>): List<Rate> {
    val copy = mutableListOf<Rate>()
    rates.forEach { copy.add(Rate(it.symbol, it.value)) }
    return copy.toList()
  }

  private fun getBaseRatePreviousValue() = previousRates.firstOrNull {
    it.symbol == baseRateSymbol
  }?.value ?: Rate.BASE_RATE_DEFAULT_VALUE

}