package com.bobowiec.revolut_app.ui.rates

import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.data.remote.RatesApiConfig
import com.bobowiec.revolut_app.data.local.RatesRepository
import com.bobowiec.revolut_app.extensions.addTo
import com.bobowiec.revolut_app.extensions.isBaseRate
import com.bobowiec.revolut_app.interactor.UpdateBaseParamValueInteractor
import com.bobowiec.revolut_app.service.RatesService
import com.bobowiec.revolut_app.util.scheduler.SchedulerProvider
import com.bobowiec.revolut_app.util.network.NetworkStateObserver
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RatesPresenter @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val ratesService: RatesService,
    private val schedulerProvider: SchedulerProvider,
    private val networkStateObserver: NetworkStateObserver,
    private val updateBaseParamValueInteractor: UpdateBaseParamValueInteractor
) {

  private var view: RatesView? = null
  private var disposables: CompositeDisposable = CompositeDisposable()
  private var fetchedRates: List<Rate> = listOf()

  fun bindView(view: RatesView) {
    this.view = view
  }

  fun unbindView() {
    this.view = null
  }

  fun init() {
    updateBaseParamValueInteractor.execute()
    loadLocalDataIfAvailable()
  }

  fun resume() {
    startUpdatingRates()
  }

  fun stop() {
    ratesRepository.save(fetchedRates)
    unbindRatesService()
    disposables.clear()
  }

  fun onRateClicked(rate: Rate) {
    if (rate.isBaseRate()) return

    networkStateObserver.determineNetworkState()
        .subscribeOn(schedulerProvider.ioScheduler())
        .observeOn(schedulerProvider.uiScheduler())
        .subscribe { isConnectedToInternet ->
          if (isConnectedToInternet) {
            RatesApiConfig.BASE_PARAM_VALUE = rate.symbol
            view?.scrollToTop()
          } else {
            view?.showOfflineSnackBar()
          }
        }
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
          bindRatesService()
          if (!isConnectedToInternet) handleOfflineError()
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

  private fun handleData(rates: List<Rate>) {
    if (rates.isEmpty()) return
    this.fetchedRates = rates
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