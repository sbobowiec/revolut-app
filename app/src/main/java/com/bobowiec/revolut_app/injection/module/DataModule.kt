package com.bobowiec.revolut_app.injection.module

import android.content.Context
import com.bobowiec.revolut_app.data.local.RatesRepository
import com.bobowiec.revolut_app.data.local.RatesRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

  @Provides
  @Singleton
  fun provideRatesRepository(context: Context, gson: Gson): RatesRepository =
      RatesRepositoryImpl(context = context, gson = gson)

}