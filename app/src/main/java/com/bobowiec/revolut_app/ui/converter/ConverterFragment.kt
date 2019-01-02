package com.bobowiec.revolut_app.ui.converter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bobowiec.revolut_app.R
import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.extensions.getAppComponent
import com.bobowiec.revolut_app.extensions.hide
import com.bobowiec.revolut_app.extensions.show
import com.bobowiec.revolut_app.ui.converter.adapter.ConvertibleRatesAdapter
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_converter.*
import kotlinx.android.synthetic.main.view_empty_data.*

class ConverterFragment : Fragment(), ConverterView {

  @Inject
  lateinit var presenter: ConverterPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    context?.getAppComponent()?.inject(this)
    presenter.bindView(this)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupRecycler()
  }

  override fun onResume() {
    super.onResume()
    presenter.onInit()
  }

  override fun onDestroy() {
    presenter.unbindView()
    super.onDestroy()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_converter, container, false)
  }

  override fun showData(data: List<Rate>) {
    (rates.adapter as ConvertibleRatesAdapter).refresh(data)
  }

  override fun showEmptyDataView() {
    empty_data_view.show()
  }

  override fun hideEmptyDataView() {
    empty_data_view.hide()
  }

  private fun setupRecycler() {
    rates.apply {
      adapter = ConvertibleRatesAdapter(presenter::onRateClicked)

      val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
      addItemDecoration(divider)

      val linearLayoutManager = LinearLayoutManager(context)
      layoutManager = linearLayoutManager
      itemAnimator = null
    }
  }

  companion object {

    const val TAG = "ConverterFragment"

    fun create() = ConverterFragment()

  }

}