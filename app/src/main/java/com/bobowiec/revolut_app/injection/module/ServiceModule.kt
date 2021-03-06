package com.bobowiec.revolut_app.injection.module

import com.bobowiec.revolut_app.data.local.RatesRepository
import com.bobowiec.revolut_app.data.remote.RatesApi
import com.bobowiec.revolut_app.service.RatesService
import com.bobowiec.revolut_app.service.RatesServiceImpl
import com.bobowiec.revolut_app.util.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule {

  @Provides
  @Singleton
  fun provideRatesService(
      api: RatesApi,
      repository: RatesRepository,
      schedulerProvider: SchedulerProvider
  ): RatesService = RatesServiceImpl(
      ratesApi = api,
      ratesRepository = repository,
      schedulerProvider = schedulerProvider
  )

}