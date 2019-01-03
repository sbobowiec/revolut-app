package com.bobowiec.revolut_app.extensions

import android.content.Context
import com.bobowiec.revolut_app.App
import com.bobowiec.revolut_app.data.model.CurrentRates
import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.data.remote.RatesApiConfig
import com.bobowiec.revolut_app.injection.ApplicationComponent
import java.math.BigDecimal

fun Context.getAppComponent(): ApplicationComponent = (this.applicationContext as App).component

fun CurrentRates.toList(): List<Rate> {
  val rates = ArrayList<Rate>()
  rates.add(Rate(this.base, Rate.BASE_RATE_DEFAULT_VALUE))
  rates.addAll(this.rates.map { Rate(it.key, it.value) })

  return rates
}

fun Rate.isBaseRate() = symbol == RatesApiConfig.BASE_PARAM_VALUE

fun Rate.multiply(multiplier: BigDecimal) {
  value = value.multiply(multiplier)
      .setScale(Rate.RATE_VALUE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP)
}