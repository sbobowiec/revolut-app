package com.bobowiec.revolut_app.ui.rates

import com.bobowiec.revolut_app.data.local.RatesRepository
import com.bobowiec.revolut_app.interactor.UpdateBaseParamValueInteractor
import com.bobowiec.revolut_app.provider.RatesDataProvider
import com.bobowiec.revolut_app.service.RatesService
import com.bobowiec.revolut_app.util.network.NetworkStateObserver
import com.bobowiec.revolut_app.util.scheduler.TestSchedulerProvider
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.BDDMockito.never
import org.mockito.BDDMockito.anyList
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RatesPresenterTest {

  @Mock private lateinit var view: RatesView
  @Mock private lateinit var ratesRepository: RatesRepository
  @Mock private lateinit var ratesService: RatesService
  @Mock private lateinit var networkStateObserver: NetworkStateObserver
  @Mock private lateinit var updateBaseParamValueInteractor: UpdateBaseParamValueInteractor
  private lateinit var testSchedulerProvider: TestSchedulerProvider

  private lateinit var SUT: RatesPresenter

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    testSchedulerProvider = TestSchedulerProvider()

    SUT = RatesPresenter(
        ratesRepository = ratesRepository,
        ratesService = ratesService,
        schedulerProvider = testSchedulerProvider,
        networkStateObserver = networkStateObserver,
        updateBaseParamValueInteractor = updateBaseParamValueInteractor)

    SUT.bindView(view)
  }

  @After
  fun tearDown() {
    SUT.unbindView()
  }

  @Test
  fun `Should load local data on init if available and update base param`() {
    // given
    val ratesList = RatesDataProvider.getRandomRates()
    given(ratesRepository.findAll()).willReturn(ratesList)

    // when
    SUT.init()

    // then
    then(updateBaseParamValueInteractor).should().execute()
    then(view).should().showData(ratesList)
  }

  @Test
  fun `Should not load local data on init if not available`() {
    // given
    given(ratesRepository.findAll()).willReturn(emptyList())

    // when
    SUT.init()

    // then
    then(view).should(never()).showData(anyList())
  }



}