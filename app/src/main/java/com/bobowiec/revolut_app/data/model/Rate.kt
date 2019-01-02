package com.bobowiec.revolut_app.data.model

import java.math.BigDecimal

data class Rate(val symbol: String, var value: BigDecimal) {

  companion object {

    const val BASE_RATE_DECIMAL_PLACES = 2

    const val EXCHANGE_RATE_DECIMAL_PLACES = 4

    val BASE_RATE_DEFAULT_VALUE: BigDecimal = BigDecimal.ONE.setScale(BASE_RATE_DECIMAL_PLACES)

  }

}