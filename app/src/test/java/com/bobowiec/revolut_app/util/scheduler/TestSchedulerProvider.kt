package com.bobowiec.revolut_app.util.scheduler

import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider(
    val testScheduler: TestScheduler = TestScheduler()
) : SchedulerProvider {

  override fun uiScheduler() = testScheduler

  override fun computationScheduler() = testScheduler

  override fun ioScheduler() = testScheduler

}