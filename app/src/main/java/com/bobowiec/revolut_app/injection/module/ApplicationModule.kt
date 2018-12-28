package com.bobowiec.revolut_app.injection.module

import android.content.Context
import com.bobowiec.revolut_app.util.scheduler.AppSchedulerProvider
import com.bobowiec.revolut_app.util.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

  @Provides
  @Singleton
  fun providesAppContext(): Context = context.applicationContext

  @Provides
  @Singleton
  fun provideSchedulerProvider() : SchedulerProvider = AppSchedulerProvider()

}