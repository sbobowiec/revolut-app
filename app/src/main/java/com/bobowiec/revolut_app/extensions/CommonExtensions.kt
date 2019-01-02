package com.bobowiec.revolut_app.extensions

import android.content.Context
import com.bobowiec.revolut_app.App
import com.bobowiec.revolut_app.data.model.CurrentRates
import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.injection.ApplicationComponent
import java.math.BigDecimal

fun Context.getAppComponent(): ApplicationComponent = (this.applicationContext as App).component

fun CurrentRates.toList(): List<Rate> {
  val rates = ArrayList<Rate>()
  rates.add(Rate(this.base, 1.00))
  rates.addAll(this.rates.map { Rate(it.key, it.value) })

  return rates
}

fun Rate.roundedValue(decimalPlaces: Int): String {
  return BigDecimal(value)
      .setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP)
      .toString()
}