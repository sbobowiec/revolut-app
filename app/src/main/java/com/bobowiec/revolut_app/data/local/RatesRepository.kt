package com.bobowiec.revolut_app.data.local

import com.bobowiec.revolut_app.data.model.Rate

interface RatesRepository {

  fun save(rates: List<Rate>)

  fun findAll(): List<Rate>

}