package com.bobowiec.revolut_app.injection.module

import com.bobowiec.revolut_app.data.remote.RatesApi
import com.bobowiec.revolut_app.data.remote.RatesApiConfig
import com.bobowiec.revolut_app.util.network.NetworkStateObserver
import com.bobowiec.revolut_app.util.network.RxNetworkStateObserver
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

  @Provides
  @Singleton
  fun provideGson() = Gson()

  @Provides
  @Singleton
  fun provideOkHttpClient() = OkHttpClient()

  @Provides
  @Singleton
  fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) : Retrofit =
      Retrofit.Builder()
          .client(okHttpClient)
          .baseUrl(RatesApiConfig.API_BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(gson))
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build()

  @Provides
  @Singleton
  fun provideRatesApi(retrofit: Retrofit): RatesApi = retrofit.create(RatesApi::class.java)

  @Provides
  @Singleton
  fun provideNetworkStateObserver(): NetworkStateObserver = RxNetworkStateObserver()

}