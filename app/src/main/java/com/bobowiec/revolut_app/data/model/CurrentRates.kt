package com.bobowiec.revolut_app.data.model

import java.math.BigDecimal

data class CurrentRates(val base: String, val date: String, val rates: Map<String, BigDecimal>)