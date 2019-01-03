package com.bobowiec.revolut_app.util.convert

import com.bobowiec.revolut_app.provider.RatesDataProvider
import com.bobowiec.revolut_app.data.model.Rate
import org.junit.Assert.*
import org.junit.Test

class RatesConverterTest {

  private lateinit var SUT: RatesConverter

  @Test
  fun `Should not convert rates if base rate not changed and has default value`() {
    // given
    val rates = RatesDataProvider.getRandomRates()
    val baseRate = rates[0]

    // when
    SUT = RatesConverter(baseRate = baseRate)
    val result = SUT.convert(rates)

    // then
    assertEquals(rates, result)
  }

  @Test
  fun `Should convert rates for same base rate with new value`() {
    // given
    val rates = RatesDataProvider.getRandomRates()
    val rateValue = RatesDataProvider.getRateValue(1.45)
    val baseRate = Rate(rates[0].symbol, rateValue)

    // when
    SUT = RatesConverter(baseRate = baseRate)
    val result = SUT.convert(rates)

    // then
    val expectedRates = RatesDataProvider.getConvertedRates(baseRate, rates)
    assertEquals(expectedRates, result)
  }

  @Test
  fun `Should convert rates for new base rate`() {
    // given
    val previousRates = RatesDataProvider.getRandomRates()
    val rates = RatesDataProvider.getRandomRates()
    val newBaseRate = rates[0]

    // when
    SUT = RatesConverter(baseRate = previousRates[0], previousRates = previousRates)
    val result = SUT.convert(rates)

    // then
    val expectedRates = RatesDataProvider.getConvertedRates(newBaseRate, rates)
    assertTrue(expectedRates.size == result.size &&
        expectedRates.containsAll(result) &&
        result.containsAll(expectedRates))
  }

}