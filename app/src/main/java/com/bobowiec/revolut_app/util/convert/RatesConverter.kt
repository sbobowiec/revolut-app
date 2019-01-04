package com.bobowiec.revolut_app.util.convert

import com.bobowiec.revolut_app.data.model.Rate
import java.math.BigDecimal

interface RatesConverter {

  fun convert(rates: List<Rate>): List<Rate>
  fun updateBaseRateValue(newBaseRateValue: BigDecimal)
  fun baseRateChanged(rates: List<Rate>): Boolean

}