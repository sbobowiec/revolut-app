package com.bobowiec.revolut_app.data.remote

import com.bobowiec.revolut_app.data.model.CurrentRates
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

  @GET("/latest")
  fun getLatestRates(@Query(BASE_PARAM) base: String = DEFAULT_BASE): Single<CurrentRates>

  companion object {
    private const val BASE_PARAM = "base"
    private const val DEFAULT_BASE = "EUR"
  }

}