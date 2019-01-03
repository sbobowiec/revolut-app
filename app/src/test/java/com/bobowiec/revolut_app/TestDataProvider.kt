package com.bobowiec.revolut_app

import com.bobowiec.revolut_app.data.model.Currency
import com.bobowiec.revolut_app.data.model.Rate
import java.math.BigDecimal
import java.util.*

object TestDataProvider {

  private val random = Random()
  private const val RANDOM_NUMBER_MIN_VALUE = 1
  private const val RANDOM_NUMBER_MAX_VALUE = 10

  fun getRandomRates(): List<Rate> =
      Currency.values().toList().map { Rate(it.symbol, randomRateValue()) }.shuffled()

  private fun randomRateValue(): BigDecimal =
      (random.nextInt(RANDOM_NUMBER_MAX_VALUE - RANDOM_NUMBER_MIN_VALUE) + RANDOM_NUMBER_MIN_VALUE)
          .toBigDecimal().setScale(Rate.BASE_RATE_VALUE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP)

}