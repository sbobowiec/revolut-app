package com.bobowiec.revolut_app.data.model

import java.math.BigDecimal

data class Rate(val symbol: String, var value: BigDecimal) {

  companion object {

    const val RATE_VALUE_DECIMAL_PLACES = 2

    val BASE_RATE_DEFAULT_VALUE: BigDecimal = BigDecimal.ONE.setScale(RATE_VALUE_DECIMAL_PLACES)

  }

}