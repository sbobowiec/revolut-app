package com.bobowiec.revolut_app.interactor

import com.bobowiec.revolut_app.data.local.RatesRepository
import com.bobowiec.revolut_app.data.remote.RatesApiConfig

class UpdateBaseParamValueInteractorImpl(
    private val ratesRepository: RatesRepository
) : UpdateBaseParamValueInteractor {

  override fun execute(value: String?) {
    if (value == null) {
      RatesApiConfig.BASE_PARAM_VALUE = ratesRepository.findAll()
          .takeIf { it.isNotEmpty() }?.first()?.symbol ?: RatesApiConfig.DEFAULT_BASE
    } else {
      RatesApiConfig.BASE_PARAM_VALUE = value
    }
  }

}