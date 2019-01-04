package com.bobowiec.revolut_app.ui.rates

import com.bobowiec.revolut_app.data.local.RatesRepository
import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.interactor.UpdateBaseParamValueInteractor
import com.bobowiec.revolut_app.provider.RatesDataProvider
import com.bobowiec.revolut_app.service.RatesService
import com.bobowiec.revolut_app.util.network.NetworkStateObserver
import com.bobowiec.revolut_app.util.scheduler.TestSchedulerProvider
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.BDDMockito.never
import org.mockito.BDDMockito.anyList
import org.mockito.BDDMockito.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.math.BigDecimal

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

  @Test
  fun `Should start observing rates on resume`() {
    // given
    given(networkStateObserver.observeNetworkState())
        .willReturn(Observable.just(true))

    val ratesList = RatesDataProvider.getRandomRates()
    given(ratesService.bind()).willReturn(Observable.just(ratesList))

    given(view.isRatesRecyclerEmpty()).willReturn(true)

    // when
    SUT.resume()
    testSchedulerProvider.testScheduler.triggerActions()

    // then
    then(ratesService).should().bind()
    then(view).should().showLoadingIndicator()
    then(view).should().hideLoadingIndicator()
    then(view).should().showData(ratesList)
  }

  @Test
  fun `Should show offline snack bar and load data from repository`() {
    // given
    given(networkStateObserver.observeNetworkState())
        .willReturn(Observable.just(false))

    val ratesList = RatesDataProvider.getRandomRates()
    given(ratesService.bind()).willReturn(Observable.just(ratesList))

    // when
    SUT.resume()
    testSchedulerProvider.testScheduler.triggerActions()

    // then
    then(ratesService).should().bind()
    then(view).should().showData(ratesList)
    then(view).should().showOfflineSnackBar()
  }

  @Test
  fun `Should show error view if no internet connection and storage is empty`() {
    // given
    given(networkStateObserver.observeNetworkState())
        .willReturn(Observable.just(false))

    given(ratesService.bind()).willReturn(Observable.just(emptyList()))

    given(view.isRatesRecyclerEmpty()).willReturn(true)

    // when
    SUT.resume()
    testSchedulerProvider.testScheduler.triggerActions()

    // then
    then(ratesService).should().bind()
    then(view).should(never()).showData(anyList())
    then(view).should().showErrorView(anyString())
  }

  @Test
  fun `Should update local storage and stop observing rates on stop`() {
    // given

    // when
    SUT.stop()

    // then
    then(ratesRepository).should().save(anyList())
    then(ratesService).should().unbind()
  }

  @Test
  fun `Should react on rate click, change base param and scroll to top`() {
    // given
    val rate = Rate(symbol = "FAKE", value = BigDecimal.ZERO)

    given(networkStateObserver.determineNetworkState())
        .willReturn(Single.just(true))

    // when
    SUT.onRateClicked(rate)
    testSchedulerProvider.testScheduler.triggerActions()

    // then
    then(updateBaseParamValueInteractor).should().execute(rate.symbol)
    then(view).should().scrollToTop()
    then(view).should(never()).showOfflineSnackBar()
  }

  @Test
  fun `Should react on rate click and show offline snack bar if internet is not available`() {
    // given
    val rate = Rate(symbol = "FAKE", value = BigDecimal.ZERO)

    given(networkStateObserver.determineNetworkState())
        .willReturn(Single.just(false))

    // when
    SUT.onRateClicked(rate)
    testSchedulerProvider.testScheduler.triggerActions()

    // then
    then(updateBaseParamValueInteractor).should(never()).execute(rate.symbol)
    then(view).should(never()).scrollToTop()
    then(view).should().showOfflineSnackBar()
  }

}