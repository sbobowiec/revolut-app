package com.bobowiec.revolut_app.data.model

import com.bobowiec.revolut_app.R

enum class Currency(val symbol: String, val fullName: String, val flagIconRes: Int) {

  EUR("EUR", "Euro", R.drawable.icon_eur),
  AUD("AUD", "Australian dollar", R.drawable.icon_aud),
  BGN("BGN", "Bulgarian lev", R.drawable.icon_bgn),
  BRL("BRL", "Brazilian real", R.drawable.icon_brl),
  CAD("CAD", "Canadian dollar", R.drawable.icon_cad),
  CHF("CHF", "Swiss franc", R.drawable.icon_chf),
  CNY("CNY", "Chinese yuan", R.drawable.icon_cny),
  CZK("CZK", "Czech koruna", R.drawable.icon_czk),
  DKK("DKK", "Danish krone", R.drawable.icon_dkk),
  GBP("GBP", "British pound", R.drawable.icon_gbp),
  HKD("HKD", "Hong Kong dollar", R.drawable.icon_hkd),
  HRK("HRK", "Croatian kuna", R.drawable.icon_hrk),
  HUF("HUF", "Hungarian forint", R.drawable.icon_huf),
  IDR("IDR", "Indonesian rupiah", R.drawable.icon_idr),
  ILS("ILS", "Israeli new shekel", R.drawable.icon_ils),
  INR("INR", "Indian rupee", R.drawable.icon_inr),
  ISK("ISK", "Icelandic króna", R.drawable.icon_isk),
  JPY("JPY", "Japanese yen", R.drawable.icon_jpy),
  KRW("KRW", "South Korean won", R.drawable.icon_krw),
  MXN("MXN", "Mexican peso", R.drawable.icon_mxn),
  MYR("MYR", "Malaysian ringgit", R.drawable.icon_myr),
  NOK("NOK", "Norwegian krone", R.drawable.icon_nok),
  NZD("NZD", "New Zealand dollar", R.drawable.icon_nzd),
  PHP("PHP", "Philippine piso", R.drawable.icon_php),
  PLN("PLN", "Polish złoty", R.drawable.icon_pln),
  RON("RON", "Romanian leu", R.drawable.icon_ron),
  RUB("RUB", "Russian ruble", R.drawable.icon_rub),
  SEK("SEK", "Swedish krona", R.drawable.icon_sek),
  SGD("SGD", "Singapore dollar", R.drawable.icon_sgd),
  THB("THB", "Thai baht", R.drawable.icon_thb),
  TRY("TRY", "Turkish lira", R.drawable.icon_try),
  USD("USD", "United States dollar", R.drawable.icon_usd),
  ZAR("ZAR", "South African rand", R.drawable.icon_zar)

}