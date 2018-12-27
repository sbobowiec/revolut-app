package com.bobowiec.revolut_app.ui.rates

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bobowiec.revolut_app.R
import com.bobowiec.revolut_app.extensions.getAppComponent
import com.bobowiec.revolut_app.ui.base.BaseFragment
import com.bobowiec.revolut_app.ui.rates.adapter.RateItem
import com.bobowiec.revolut_app.ui.rates.adapter.RateItemsAdapter
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_rates.*

class RatesFragment : BaseFragment(), RatesView {

  @Inject
  lateinit var presenter: RatesPresenter

  private val rateItemsAdapter = RateItemsAdapter()

  override fun getTitle() = R.string.title_fragment_rates

  override fun getFragmentTag() = TAG

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    context?.getAppComponent()?.inject(this)
    presenter.bindView(this)
  }

  override fun onDestroy() {
    presenter.unbindView()
    super.onDestroy()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_rates, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupRecycler()
  }

  override fun onResume() {
    super.onResume()
    presenter.onInit()
  }

  override fun isRatesRecyclerEmpty() = rateItemsAdapter.itemCount == 0

  override fun addRates(rates: List<RateItem>) {
    rateItemsAdapter.addItems(rates)
  }

  private fun setupRecycler() {
    rates.apply {
      adapter = rateItemsAdapter

      val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
      addItemDecoration(divider)

      val linearLayoutManager = LinearLayoutManager(context)
      layoutManager = linearLayoutManager
      itemAnimator = null
    }
  }

  companion object {

    private const val TAG = "RatesFragment"

    fun create() = RatesFragment()

  }

}