package com.bobowiec.revolut_app.injection.module

import com.bobowiec.revolut_app.data.repository.RatesRepository
import com.bobowiec.revolut_app.data.repository.RatesRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

  @Provides
  @Singleton
  fun provideRatesRepository(): RatesRepository = RatesRepositoryImpl()

}