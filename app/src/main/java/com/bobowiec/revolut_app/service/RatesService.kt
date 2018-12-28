package com.bobowiec.revolut_app.service

import com.bobowiec.revolut_app.data.model.Rate
import io.reactivex.Observable

interface RatesService {

  fun bind(): Observable<List<Rate>>
  fun unbind()

}