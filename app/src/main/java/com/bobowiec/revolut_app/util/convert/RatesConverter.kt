package com.bobowiec.revolut_app.util.convert

import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.extensions.multiply
import com.bobowiec.revolut_app.extensions.roundToBase
import java.math.BigDecimal

class RatesConverter(private var baseRate: Rate = Rate.BASE_RATE) {

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

  fun updateBaseRateValue(newBaseRateValue: BigDecimal) {
    baseRate.value = newBaseRateValue.setScale(
        Rate.BASE_RATE_VALUE_DECIMAL_PLACES,
        BigDecimal.ROUND_HALF_UP
    )
  }

  fun baseRateChanged(rates: List<Rate>): Boolean =
      rates.isNotEmpty() && rates[0].symbol != baseRate.symbol

  private fun convertForNewBaseRate(rates: List<Rate>): List<Rate> {
    val result = copyRates(rates)

    baseRate.symbol = result[0].symbol
    baseRate.value = getBaseRatePreviousValue()
    result[0].value = baseRate.value
    result.subList(1, result.size).map { it.multiply(baseRate.value) }

    return result.toList()
  }

  private fun convertForPreviousBaseRate(rates: List<Rate>): List<Rate> {
    val result = copyRates(rates)

    result.first().value = baseRate.value
    result.subList(1, result.size).map { it.multiply(baseRate.value) }

    return result.toList()
  }

  private fun copyRates(rates: List<Rate>): List<Rate> {
    val copy = mutableListOf<Rate>()
    rates.forEach { copy.add(Rate(it.symbol, it.value)) }
    return copy.toList()
  }

  private fun getBaseRatePreviousValue() =
      previousRates.firstOrNull {
        it.symbol == baseRate.symbol
      }?.roundToBase() ?: Rate.BASE_RATE_DEFAULT_VALUE

}