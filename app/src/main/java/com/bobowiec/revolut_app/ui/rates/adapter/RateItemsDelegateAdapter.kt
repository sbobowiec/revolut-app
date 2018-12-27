package com.bobowiec.revolut_app.ui.rates.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bobowiec.revolut_app.R
import com.bobowiec.revolut_app.extensions.inflate
import com.bobowiec.revolut_app.ui.common.ViewType
import com.bobowiec.revolut_app.ui.common.ViewTypeDelegateAdapter

import kotlinx.android.synthetic.main.view_rate_item.view.*

class RateItemsDelegateAdapter : ViewTypeDelegateAdapter {

  override fun onCreateViewHolder(parent: ViewGroup) = RateItemsViewHolder(parent)

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    holder as RateItemsViewHolder
    holder.bind(item as RateItem)
  }

  inner class RateItemsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
      parent.inflate(R.layout.view_rate_item)) {

    fun bind(item: RateItem) = with(itemView) {
      rate_symbol.text = item.symbol
      rate_value.text = item.value.toString()
    }

  }

}