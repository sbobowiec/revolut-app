package com.bobowiec.revolut_app.util.network

import io.reactivex.Observable
import io.reactivex.Single

interface NetworkStateObserver {

  fun determineNetworkState(): Single<Boolean>
  fun observeNetworkState(): Observable<Boolean>

}