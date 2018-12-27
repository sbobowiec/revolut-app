package com.bobowiec.revolut_app.data.repository

import com.bobowiec.revolut_app.data.model.Rate

class RatesRepositoryImpl(private var memoryCache: List<Rate> = listOf()) : RatesRepository {

  override fun saveRates(rates: List<Rate>) {
    memoryCache = rates
  }

  override fun getRates(): List<Rate> {
    return memoryCache
  }

}