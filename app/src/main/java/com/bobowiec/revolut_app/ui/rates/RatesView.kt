package com.bobowiec.revolut_app.ui.rates

import com.bobowiec.revolut_app.ui.base.MvpView
import com.bobowiec.revolut_app.ui.rates.adapter.RateItem

interface RatesView : MvpView {

  fun isRatesRecyclerEmpty(): Boolean

  fun addRates(rates: List<RateItem>)

}