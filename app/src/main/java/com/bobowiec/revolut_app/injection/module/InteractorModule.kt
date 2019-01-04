package com.bobowiec.revolut_app.injection.module

import com.bobowiec.revolut_app.data.local.RatesRepository
import com.bobowiec.revolut_app.interactor.UpdateBaseParamValueInteractor
import com.bobowiec.revolut_app.interactor.UpdateBaseParamValueInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

  @Provides
  @Singleton
  fun provideUpdateBaseParamValueInteractor(ratesRepository: RatesRepository):
      UpdateBaseParamValueInteractor = UpdateBaseParamValueInteractorImpl(ratesRepository)

}