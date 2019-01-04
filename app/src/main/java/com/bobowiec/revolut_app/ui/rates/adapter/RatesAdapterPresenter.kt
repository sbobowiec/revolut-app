package com.bobowiec.revolut_app.ui.rates.adapter

import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.util.convert.RatesConverter
import com.bobowiec.revolut_app.util.convert.RatesConverterImpl

class RatesAdapterPresenter(private val ratesConverter: RatesConverter = RatesConverterImpl()) {

  private var view: RatesAdapterView? = null

  fun bindView(view: RatesAdapterView) {
    this.view = view
  }

  fun unbindView() {
    this.view = null
  }

  fun handleRatesRefresh(rates: List<Rate>) {
    val baseRateChanged = ratesConverter.baseRateChanged(rates)
    view?.setRates(ratesConverter.convert(rates))

    if (baseRateChanged) {
      view?.refreshAllRates()
    } else {
      view?.refreshExchangeRates()
    }
  }

  fun onRateInputValueChanged(rateInput: String) {
    val newBaseRate = rateInput.toBigDecimalOrNull() ?: Rate.BASE_RATE_DEFAULT_VALUE
    ratesConverter.updateBaseRateValue(newBaseRate)
  }

}