package com.bobowiec.revolut_app.ui.rates

import android.os.Bundle
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
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_rates.*
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import com.bobowiec.revolut_app.extensions.showSnackbar
import com.bobowiec.revolut_app.ui.rates.adapter.RatesAdapter
import kotlinx.android.synthetic.main.view_error.*

class RatesFragment : Fragment(), RatesView {

  @Inject
  lateinit var presenter: RatesPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    context?.getAppComponent()?.inject(this)
    presenter.bindView(this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_rates, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupRecycler()
    presenter.init()
  }

  override fun onResume() {
    super.onResume()
    presenter.resume()
  }

  override fun onStop() {
    presenter.stop()
    super.onStop()
  }

  override fun onDestroy() {
    presenter.unbindView()
    super.onDestroy()
  }

  override fun scrollToTop() {
    rates.smoothScrollToPosition(0)
  }

  override fun isRatesRecyclerEmpty(): Boolean = rates.adapter.itemCount == 0

  override fun getRates(): List<Rate> = (rates.adapter as RatesAdapter).items

  override fun showData(data: List<Rate>) {
    (rates.adapter as RatesAdapter).refresh(data)
  }

  override fun showErrorView(message: String) {
    error_message.text = message
    error_view.show()
  }

  override fun hideErrorView() {
    error_view.hide()
  }

  override fun showOfflineSnackBar() {
    val snackBarLayout = activity?.findViewById(R.id.snackbar_container) as CoordinatorLayout
    snackBarLayout.showSnackbar(
        getString(R.string.message_no_internet_connection),
        Snackbar.LENGTH_LONG,
        R.color.red)
  }

  override fun showLoadingIndicator() {
    loading_indicator.show()
  }

  override fun hideLoadingIndicator() {
    loading_indicator.hide()
  }

  private fun setupRecycler() {
    rates.apply {
      adapter = RatesAdapter(onRateClickListener = presenter::onRateClicked)

      val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
      addItemDecoration(divider)

      val linearLayoutManager = LinearLayoutManager(context)
      layoutManager = linearLayoutManager
      itemAnimator = null
    }
  }

  companion object {

    const val TAG = "RatesFragment"

    fun create() = RatesFragment()

  }

}