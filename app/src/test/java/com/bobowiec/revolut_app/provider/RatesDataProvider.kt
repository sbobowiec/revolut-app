package com.bobowiec.revolut_app.provider

import com.bobowiec.revolut_app.data.model.Currency
import com.bobowiec.revolut_app.data.model.CurrentRates
import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.extensions.multiply
import java.math.BigDecimal
import java.util.*

object RatesDataProvider {

  private val random = Random()
  private const val RANDOM_NUMBER_MIN_VALUE = 1
  private const val RANDOM_NUMBER_MAX_VALUE = 10

  fun getRandomCurrentRates(): CurrentRates {
    val randomRates = getRandomRates()
    return CurrentRates(
        randomRates[0].symbol,
        "",
        randomRates.subList(1, randomRates.size - 1).map { it.symbol to it.value }.toMap()
    )
  }

  fun getRandomRates(): List<Rate> {
    val randomRates = Currency.values().toList().map {
      Rate(it.symbol, randomRateValue())
    }.shuffled()

    randomRates.first().apply { this.value = Rate.BASE_RATE_DEFAULT_VALUE }
    return randomRates
  }

  fun getRateValue(value: Double): BigDecimal =
      BigDecimal.valueOf(value).setScale(Rate.BASE_RATE_VALUE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP)

  fun getConvertedRates(baseRate: Rate, rates: List<Rate>) =
      rates.mapIndexed { index, rate ->
        if (index == 0) rate.value = baseRate.value
        if (index != 0) rate.multiply(baseRate.value)
        rate
      }

  private fun randomRateValue(): BigDecimal =
      (random.nextInt(
          RANDOM_NUMBER_MAX_VALUE - RANDOM_NUMBER_MIN_VALUE) + RANDOM_NUMBER_MIN_VALUE)
          .toBigDecimal().setScale(Rate.EXCHANGE_RATE_VALUE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP)

}