package com.bobowiec.revolut_app.util.network

import io.reactivex.Observable

interface NetworkStateObserver {

  fun observeNetworkState(): Observable<Boolean>

}