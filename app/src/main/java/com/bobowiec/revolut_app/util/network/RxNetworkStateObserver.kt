package com.bobowiec.revolut_app.util.network

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable
import io.reactivex.Single

class RxNetworkStateObserver : NetworkStateObserver {

  override fun determineNetworkState(): Single<Boolean> =
      ReactiveNetwork.checkInternetConnectivity()

  override fun observeNetworkState(): Observable<Boolean> =
      ReactiveNetwork.observeInternetConnectivity()

}