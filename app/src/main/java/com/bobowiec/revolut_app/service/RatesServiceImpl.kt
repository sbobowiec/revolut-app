package com.bobowiec.revolut_app.service

import android.util.Log
import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.data.remote.RatesApi
import com.bobowiec.revolut_app.data.repository.RatesRepository
import com.bobowiec.revolut_app.extensions.addTo
import com.bobowiec.revolut_app.extensions.toList
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class RatesServiceImpl(
    private val ratesApi: RatesApi,
    private val ratesRepository: RatesRepository,
    private val ratesSubject: PublishSubject<List<Rate>> = PublishSubject.create(),
    private var timerDisposable: Disposable? = null,
    private val requestDisposables: CompositeDisposable = CompositeDisposable()
) : RatesService {

  override fun bind(): Observable<List<Rate>> {
    observeRatesChanges()
    return ratesSubject
  }

  override fun unbind() {
    ratesSubject.onComplete()
    requestDisposables.clear()
    timerDisposable?.dispose()
  }

  private fun observeRatesChanges() {
    timerDisposable = buildTimer().subscribe { handleRatesRequest() }
  }

  private fun buildTimer() = Observable.interval(API_CALL_INTERVAL, TimeUnit.SECONDS)

  private fun handleRatesRequest() {
    ratesApi.getLatestRates().map {
      it.toList()
    }
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.computation())
    .subscribe(
      { rates ->
        ratesRepository.saveRates(rates)
        ratesSubject.onNext(rates)
      },
      { error ->
        Log.e(TAG, error.message)
        requestDisposables.clear()
      }
    ).addTo(requestDisposables)
  }

  companion object {
    private const val TAG = "RatesServiceImpl"
    private const val API_CALL_INTERVAL = 1L
  }

}