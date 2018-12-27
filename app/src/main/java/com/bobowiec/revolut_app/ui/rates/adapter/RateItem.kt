package com.bobowiec.revolut_app.ui.rates.adapter

import com.bobowiec.revolut_app.ui.common.AdapterConstants
import com.bobowiec.revolut_app.ui.common.ViewType

class RateItem(val symbol: String, val value: Float) : ViewType {

  override fun getViewType() = AdapterConstants.RATE_ITEM_TYPE

}