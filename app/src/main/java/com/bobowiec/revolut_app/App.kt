package com.bobowiec.revolut_app

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.bobowiec.revolut_app.injection.ApplicationComponent
import com.bobowiec.revolut_app.injection.DaggerApplicationComponent
import com.bobowiec.revolut_app.injection.module.ApplicationModule
import io.reactivex.plugins.RxJavaPlugins

class App : Application() {

  lateinit var component: ApplicationComponent

  override fun onCreate() {
    super.onCreate()
    setupAppComponent()
    setupRxJavaErrorHandler()
  }

  private fun setupAppComponent() {
    component = DaggerApplicationComponent.builder()
        .applicationModule(ApplicationModule(this))
        .build()
  }

  private fun setupRxJavaErrorHandler() {
    RxJavaPlugins.setErrorHandler { error ->
      Handler(Looper.getMainLooper()).post {
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
      }
    }
  }

}