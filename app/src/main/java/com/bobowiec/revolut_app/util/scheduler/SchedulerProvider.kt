package com.bobowiec.revolut_app.util.scheduler

import io.reactivex.Scheduler

interface SchedulerProvider {

  fun uiScheduler() : Scheduler
  fun computationScheduler(): Scheduler
  fun ioScheduler() : Scheduler

}
