package com.bobowiec.revolut_app.injection

import com.bobowiec.revolut_app.injection.module.*
import com.bobowiec.revolut_app.ui.rates.RatesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
  ApplicationModule::class,
  DataModule::class,
  NetworkModule::class,
  ServiceModule::class,
  InteractorModule::class
])
interface ApplicationComponent {

  fun inject(fragment: RatesFragment)

}