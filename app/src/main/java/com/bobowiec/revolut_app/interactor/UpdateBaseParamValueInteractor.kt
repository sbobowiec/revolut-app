package com.bobowiec.revolut_app.interactor

import com.bobowiec.revolut_app.data.local.RatesRepository
import com.bobowiec.revolut_app.data.remote.RatesApiConfig

interface UpdateBaseParamValueInteractor {
  fun execute()
}

class UpdateBaseParamValueInteractorImpl(
    private val ratesRepository: RatesRepository
) : UpdateBaseParamValueInteractor {

  override fun execute() {
    RatesApiConfig.BASE_PARAM_VALUE = ratesRepository.findAll()
        .takeIf { it.isNotEmpty() }?.first()?.symbol ?: RatesApiConfig.DEFAULT_BASE
  }

}