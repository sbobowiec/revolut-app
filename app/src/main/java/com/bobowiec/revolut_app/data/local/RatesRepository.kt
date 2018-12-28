package com.bobowiec.revolut_app.data.local

import com.bobowiec.revolut_app.data.model.Rate

interface RatesRepository {

  fun saveRates(rates: List<Rate>)

  fun getRates(): List<Rate>

  fun syncRates()

}