package com.bobowiec.revolut_app.ui.common

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bobowiec.revolut_app.R
import com.bobowiec.revolut_app.extensions.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

  override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = LoadingViewHolder(parent)

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) { }

  class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
      parent.inflate(R.layout.view_loading_item)
  )

}