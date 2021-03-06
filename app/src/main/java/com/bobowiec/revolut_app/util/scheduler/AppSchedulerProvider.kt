package com.bobowiec.revolut_app.util.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulerProvider : SchedulerProvider {

  override fun ioScheduler() = Schedulers.io()

  override fun computationScheduler() = Schedulers.computation()

  override fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()

}