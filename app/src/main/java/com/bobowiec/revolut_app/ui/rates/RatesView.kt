package com.bobowiec.revolut_app.ui.rates

import com.bobowiec.revolut_app.data.model.Rate

interface RatesView {

  fun scrollToTop()
  fun isRatesRecyclerEmpty(): Boolean
  fun showData(data: List<Rate>)
  fun showErrorView(message: String)
  fun hideErrorView()
  fun showOfflineSnackBar()
  fun showLoadingIndicator()
  fun hideLoadingIndicator()

}