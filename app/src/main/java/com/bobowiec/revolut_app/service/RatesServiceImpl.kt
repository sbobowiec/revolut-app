package com.bobowiec.revolut_app.service

import com.bobowiec.revolut_app.data.local.RatesRepository
import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.data.remote.RatesApi
import com.bobowiec.revolut_app.extensions.addTo
import com.bobowiec.revolut_app.extensions.toList
import com.bobowiec.revolut_app.util.scheduler.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class RatesServiceImpl(
    private val ratesApi: RatesApi,
    private val ratesRepository: RatesRepository,
    private val schedulerProvider: SchedulerProvider,
    private val ratesSubject: PublishSubject<List<Rate>> = PublishSubject.create(),
    private var timerDisposable: Disposable? = null,
    private val requestDisposables: CompositeDisposable = CompositeDisposable()
) : RatesService {

  private var lastRates: List<Rate> = listOf()

  override fun bind(): Observable<List<Rate>> {
    cancelCurrentTimerIfRunning()
    observeRatesChanges()
    return ratesSubject
  }

  override fun unbind() {
    cancelRequests()
    cancelCurrentTimerIfRunning()
  }

  private fun observeRatesChanges() {
    timerDisposable = buildTimer().subscribe { handleRatesRequest() }
  }

  private fun buildTimer() = Observable.interval(API_CALL_INTERVAL, TimeUnit.SECONDS)

  private fun cancelCurrentTimerIfRunning() {
    timerDisposable?.dispose()
  }

  private fun cancelRequests() {
    requestDisposables.clear()
  }

  private fun handleRatesRequest() {
    ratesApi.getLatestRates().map { it.toList() }
        .subscribeOn(schedulerProvider.ioScheduler())
        .observeOn(schedulerProvider.computationScheduler())
        .doOnSuccess { lastRates = it }
        .onErrorResumeNext { Single.just(getLastRates()) }
        .subscribe { rates -> ratesSubject.onNext(rates) }
        .addTo(requestDisposables)
  }

  private fun getLastRates(): List<Rate> {
    if (lastRates.isEmpty()) {
      lastRates = ratesRepository.findAll()
    }
    return lastRates
  }

  companion object {
    private const val API_CALL_INTERVAL = 1L
  }

}