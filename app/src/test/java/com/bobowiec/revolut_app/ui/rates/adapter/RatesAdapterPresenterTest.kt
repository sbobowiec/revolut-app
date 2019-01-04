package com.bobowiec.revolut_app.ui.rates.adapter

import com.bobowiec.revolut_app.provider.RatesDataProvider
import com.bobowiec.revolut_app.util.convert.RatesConverter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RatesAdapterPresenterTest {

  @Mock private lateinit var view: RatesAdapterView
  @Mock private lateinit var ratesConverter: RatesConverter

  private lateinit var SUT: RatesAdapterPresenter

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    SUT = RatesAdapterPresenter(ratesConverter)
    SUT.bindView(view)
  }

  @After
  fun tearDown() {
    SUT.unbindView()
  }

  @Test
  fun `Should refresh all rates if base rate has been changed`() {
    // given
    val ratesList = RatesDataProvider.getRandomRates()
    given(ratesConverter.baseRateChanged(ratesList)).willReturn(true)

    // when
    SUT.handleRatesRefresh(ratesList)

    // then
    then(view).should().setRates(ratesList)
    then(view).should().refreshAllRates()
  }

}