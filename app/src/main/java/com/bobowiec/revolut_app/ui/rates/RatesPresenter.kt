package com.bobowiec.revolut_app.ui.rates

import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.data.remote.RatesApiConfig
import com.bobowiec.revolut_app.data.local.RatesRepository
import com.bobowiec.revolut_app.extensions.addTo
import com.bobowiec.revolut_app.service.RatesService
import com.bobowiec.revolut_app.ui.base.BasePresenter
import com.bobowiec.revolut_app.util.scheduler.SchedulerProvider
import com.bobowiec.revolut_app.util.network.NetworkStateObserver
import javax.inject.Inject

class RatesPresenter @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val ratesService: RatesService,
    private val schedulerProvider: SchedulerProvider,
    private val networkStateObserver: NetworkStateObserver
) : BasePresenter<RatesView>() {

  private var rates: List<Rate> = listOf()

  init {
    RatesApiConfig.BASE_PARAM_VALUE = ratesRepository.findAll()
        .takeIf { it.isNotEmpty() }?.first()?.symbol ?: RatesApiConfig.DEFAULT_BASE
  }

  fun onInit() {
    loadLocalDataIfAvailable()
    startUpdatingRates()
  }

  fun onStop() {
    ratesRepository.save(rates)
    unbindRatesService()
  }

  fun onRateClicked(rate: Rate) {
    RatesApiConfig.BASE_PARAM_VALUE = rate.symbol
    view?.scrollToTop()
  }

  private fun loadLocalDataIfAvailable() {
    ratesRepository.findAll()
        .takeIf { it.isNotEmpty() }
        ?.let { handleData(it) }
  }

  private fun startUpdatingRates() {
    networkStateObserver.observeNetworkState()
        .subscribeOn(schedulerProvider.ioScheduler())
        .observeOn(schedulerProvider.uiScheduler())
        .subscribe { isConnectedToInternet ->
          if (isConnectedToInternet) {
            bindRatesService()
          } else {
            onInternetConnectionLost()
          }
        }.addTo(disposables)
  }

  private fun bindRatesService() {
    ratesService.bind()
        .subscribeOn(schedulerProvider.ioScheduler())
        .observeOn(schedulerProvider.uiScheduler())
        .doOnSubscribe { handleLoading() }
        .subscribe { handleData(it) }
        .addTo(disposables)
  }

  private fun unbindRatesService() {
    ratesService.unbind()
  }

  private fun onInternetConnectionLost() {
    unbindRatesService()
    handleOfflineError()
  }

  private fun handleData(rates: List<Rate>) {
    this.rates = rates
    view?.hideErrorView()
    view?.hideLoadingIndicator()
    view?.showData(rates)
  }

  private fun handleLoading() {
    if (isEmptyData()) {
      view?.hideErrorView()
      view?.showLoadingIndicator()
    }
  }

  private fun handleOfflineError() {
    if (isEmptyData()) {
      view?.showErrorView(NO_INTERNET_CONNECTION_MSG)
    } else {
      view?.showOfflineSnackBar()
    }
  }

  private fun isEmptyData() = view?.isRatesRecyclerEmpty() ?: true

  companion object {
    private const val NO_INTERNET_CONNECTION_MSG = "No internet connection"
  }

}