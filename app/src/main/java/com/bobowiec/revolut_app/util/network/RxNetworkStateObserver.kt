package com.bobowiec.revolut_app.util.network

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable

class RxNetworkStateObserver : NetworkStateObserver {

  override fun observeNetworkState(): Observable<Boolean> =
      ReactiveNetwork.observeInternetConnectivity()

}