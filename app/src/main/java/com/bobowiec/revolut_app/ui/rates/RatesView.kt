package com.bobowiec.revolut_app.ui.rates

import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.ui.base.MvpView

interface RatesView : MvpView {

  fun scrollToTop()
  fun isRatesRecyclerEmpty(): Boolean
  fun showData(data: List<Rate>)
  fun showErrorView(message: String)
  fun hideErrorView()
  fun showOfflineSnackBar()
  fun showLoadingIndicator()
  fun hideLoadingIndicator()

}