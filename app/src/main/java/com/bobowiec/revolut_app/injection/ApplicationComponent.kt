package com.bobowiec.revolut_app.injection

import com.bobowiec.revolut_app.injection.module.ApplicationModule
import com.bobowiec.revolut_app.injection.module.DataModule
import com.bobowiec.revolut_app.injection.module.NetworkModule
import com.bobowiec.revolut_app.injection.module.ServiceModule
import com.bobowiec.revolut_app.ui.converter.ConverterFragment
import com.bobowiec.revolut_app.ui.rates.RatesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
  ApplicationModule::class,
  DataModule::class,
  NetworkModule::class,
  ServiceModule::class
])
interface ApplicationComponent {

  fun inject(fragment: RatesFragment)

  fun inject(fragment: ConverterFragment)

}