package com.bobowiec.revolut_app.data.remote

object RatesApiConfig {

  const val API_BASE_URL = "https://revolut.duckdns.org"

  const val BASE_PARAM_NAME = "base"

  private const val DEFAULT_BASE = "EUR"
  var BASE_PARAM_VALUE = DEFAULT_BASE

}