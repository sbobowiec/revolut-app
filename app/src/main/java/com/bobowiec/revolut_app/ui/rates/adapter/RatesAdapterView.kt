package com.bobowiec.revolut_app.ui.rates.adapter

import com.bobowiec.revolut_app.data.model.Rate

interface RatesAdapterView {

  fun setRates(rates: List<Rate>)
  fun refreshAllRates()
  fun refreshExchangeRates()

}