package com.bobowiec.revolut_app.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bobowiec.revolut_app.R
import com.bobowiec.revolut_app.ui.rates.RatesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    showRatesFragment()
  }

  private fun showRatesFragment() {
    supportFragmentManager.beginTransaction().apply {
      replace(R.id.container, RatesFragment.create(), RatesFragment.TAG)
    }.commit()
  }

}
