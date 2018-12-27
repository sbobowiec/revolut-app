package com.bobowiec.revolut_app.util

import io.reactivex.Scheduler

interface SchedulerProvider {

  fun uiScheduler() : Scheduler

  fun ioScheduler() : Scheduler

}
