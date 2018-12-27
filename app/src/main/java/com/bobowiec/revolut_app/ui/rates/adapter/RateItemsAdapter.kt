package com.bobowiec.revolut_app.ui.rates.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bobowiec.revolut_app.ui.common.AdapterConstants
import com.bobowiec.revolut_app.ui.common.LoadingDelegateAdapter
import com.bobowiec.revolut_app.ui.common.ViewType
import com.bobowiec.revolut_app.ui.common.ViewTypeDelegateAdapter

class RateItemsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var items: MutableList<ViewType> = arrayListOf()
  private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

  private val loadingItem = object : ViewType {
    override fun getViewType() = AdapterConstants.LOADING_ITEM_TYPE
  }

  init {
    delegateAdapters.put(AdapterConstants.LOADING_ITEM_TYPE, LoadingDelegateAdapter())
    delegateAdapters.put(AdapterConstants.RATE_ITEM_TYPE, RateItemsDelegateAdapter())
    items.add(loadingItem)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      delegateAdapters.get(viewType).onCreateViewHolder(parent)

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val viewType = getItemViewType(position)
    val delegateAdapter: ViewTypeDelegateAdapter = delegateAdapters.get(viewType)
    delegateAdapter.onBindViewHolder(holder, items[position])
  }

  override fun getItemCount() = items.count()

  override fun getItemViewType(position: Int) = items[position].getViewType()

  fun addItems(rateItems: List<RateItem>) {
    items.clear()
    items.addAll(rateItems)
    notifyDataSetChanged()
  }

}