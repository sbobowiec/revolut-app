package com.bobowiec.revolut_app.service

import com.bobowiec.revolut_app.data.local.RatesRepository
import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.data.remote.RatesApi
import com.bobowiec.revolut_app.extensions.toList
import com.bobowiec.revolut_app.provider.RatesDataProvider
import com.bobowiec.revolut_app.util.scheduler.TestSchedulerProvider
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class RatesServiceTest {

  @Mock private lateinit var ratesApi: RatesApi
  @Mock private lateinit var ratesRepository: RatesRepository
  private lateinit var testSchedulerProvider: TestSchedulerProvider
  private lateinit var ratesSubject: PublishSubject<List<Rate>>
  private lateinit var timer: Observable<Long>
  private var timerDisposable: Disposable? = null
  private lateinit var requestDisposables: CompositeDisposable

  private lateinit var SUT: RatesService

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)
    testSchedulerProvider = TestSchedulerProvider()
    ratesSubject = PublishSubject.create()
    requestDisposables = CompositeDisposable()
  }

  @Test
  fun `Should start observing rates changes and receive 3 different rates lists`() {
    // given
    val interval = 1L
    val actionsCount = 3L
    timer = buildTimer(interval, actionsCount)

    val randomRates1 = RatesDataProvider.getRandomCurrentRates()
    val ratesList1 = randomRates1.toList()

    val randomRates2 = RatesDataProvider.getRandomCurrentRates()
    val ratesList2 = randomRates2.toList()

    val randomRates3 = RatesDataProvider.getRandomCurrentRates()
    val ratesList3 = randomRates3.toList()

    given(ratesApi.getLatestRates())
        .willReturn(Single.just(randomRates1))
        .willReturn(Single.just(randomRates2))
        .willReturn(Single.just(randomRates3))

    given(ratesRepository.findAll()).willReturn(emptyList())

    // when
    SUT = provideRatesService()

    val testObserver = TestObserver<List<Rate>>()
    SUT.bind().subscribe(testObserver)

    testSchedulerProvider.testScheduler.advanceTimeBy(actionsCount, TimeUnit.SECONDS)

    SUT.unbind()

    // then
    testObserver.assertValues(ratesList1, ratesList2, ratesList3)
    testObserver.assertNoErrors()
    testObserver.assertOf { timerDisposable?.dispose() }
    testObserver.assertOf { requestDisposables.clear() }
  }

  @Test
  fun `Should start observing rates changes, throw error and emit values stored in repository`() {
    // given
    val interval = 1L
    val actionsCount = 3L
    timer = buildTimer(interval, actionsCount)

    val ratesList = RatesDataProvider.getRandomRates()
    given(ratesRepository.findAll()).willReturn(ratesList)

    given(ratesApi.getLatestRates()).willReturn(Single.error(Throwable()))

    // when
    SUT = provideRatesService()

    val testObserver = TestObserver<List<Rate>>()
    SUT.bind().subscribe(testObserver)

    testSchedulerProvider.testScheduler.advanceTimeBy(actionsCount, TimeUnit.SECONDS)

    SUT.unbind()

    // then
    testObserver.assertValues(ratesList, ratesList, ratesList)
    testObserver.assertNoErrors()
    testObserver.assertOf { timerDisposable?.dispose() }
    testObserver.assertOf { requestDisposables.clear() }
  }

  private fun buildTimer(interval: Long, actionsCount: Long) =
      Observable.interval(
          interval,
          TimeUnit.SECONDS,
          testSchedulerProvider.testScheduler
      ).take(actionsCount)

  private fun provideRatesService() =
      RatesServiceImpl(
          ratesApi = ratesApi,
          ratesRepository = ratesRepository,
          schedulerProvider = testSchedulerProvider,
          ratesSubject = ratesSubject,
          timer = timer,
          timerDisposable = timerDisposable,
          requestDisposables = requestDisposables
      )

}