package com.bobowiec.revolut_app.util.convert

import com.bobowiec.revolut_app.TestDataProvider
import com.bobowiec.revolut_app.extensions.multiply
import org.junit.Assert.assertEquals
import org.junit.Test

class RatesConverterTest {

  private lateinit var SUT: RatesConverter

  @Test
  fun `Should convert rates using default base rate for first time`() {
    // given
    val rates = TestDataProvider.getRandomRates()
    val baseRate = rates[0]

    // when
    SUT = RatesConverter(baseRate = baseRate)
    val result = SUT.convert(rates)

    // then
    assertEquals(baseRate, result.first())
    rates.forEachIndexed { index, item ->
      val expected = rates[index]
      if (index != 0) expected.multiply(baseRate.value)
      assertEquals(expected, item)
    }
  }

}