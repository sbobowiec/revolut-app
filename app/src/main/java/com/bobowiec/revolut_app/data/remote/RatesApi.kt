package com.bobowiec.revolut_app.data.remote

import com.bobowiec.revolut_app.data.model.CurrentRates
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

  @GET("/latest")
  fun getLatestRates(
      @Query(RatesApiConfig.BASE_PARAM_NAME)
      base: String = RatesApiConfig.BASE_PARAM_VALUE
  ): Single<CurrentRates>

}