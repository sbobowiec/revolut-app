package com.bobowiec.revolut_app.ui.converter

import com.bobowiec.revolut_app.data.local.RatesRepository
import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.ui.base.BasePresenter
import javax.inject.Inject

class ConverterPresenter @Inject constructor(
    private val ratesRepository: RatesRepository
) : BasePresenter<ConverterView>() {

  fun onInit() {
    ratesRepository.findAll().apply {
      if (isEmpty()) handleEmptyData() else handleData(this)
    }
  }

  fun onRateClicked(rate: Rate) {
    // todo implement
  }

  private fun handleEmptyData() {
    view?.showEmptyDataView()
  }

  private fun handleData(rates: List<Rate>) {
    view?.hideEmptyDataView()
    view?.showData(rates)
  }

}