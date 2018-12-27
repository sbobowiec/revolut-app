package com.bobowiec.revolut_app.ui.rates

import com.bobowiec.revolut_app.data.repository.RatesRepository
import com.bobowiec.revolut_app.extensions.addTo
import com.bobowiec.revolut_app.service.RatesService
import com.bobowiec.revolut_app.ui.base.BasePresenter
import com.bobowiec.revolut_app.ui.rates.adapter.RateItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RatesPresenter @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val ratesService: RatesService
) : BasePresenter<RatesView>() {

  fun onInit() {
    val rates = ratesRepository.getRates()
    if (rates.isNotEmpty()) {
      view?.addRates(rates.map { RateItem(it.symbol, it.value) })
    }

    ratesService.bind()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          view?.addRates(it.map { RateItem(it.symbol, it.value) })
        }.addTo(disposables)
  }

}