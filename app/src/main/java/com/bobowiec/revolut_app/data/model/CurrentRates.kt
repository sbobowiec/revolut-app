package com.bobowiec.revolut_app.data.model

data class CurrentRates(val base: String, val date: String, val rates: Map<String, Double>)