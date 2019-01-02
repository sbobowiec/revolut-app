package com.bobowiec.revolut_app.data.model

data class Rate(val symbol: String, var value: Double) {

  companion object {

    const val DEFAULT_VALUE = "1.00"

    const val BASE_RATE_DECIMAL_PLACES = 2

    const val EXCHANGE_RATE_DECIMAL_PLACES = 4

  }

}