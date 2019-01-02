package com.bobowiec.revolut_app.ui.converter

import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.ui.base.MvpView

interface ConverterView : MvpView {

  fun showData(data: List<Rate>)
  fun showEmptyDataView()
  fun hideEmptyDataView()

}