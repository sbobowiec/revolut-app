package com.bobowiec.revolut_app.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.bobowiec.revolut_app.R
import com.bobowiec.revolut_app.ui.converter.ConverterFragment
import com.bobowiec.revolut_app.ui.rates.RatesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupListeners()
    changeFragment(RatesFragment.create(), RatesFragment.TAG)
  }

  private fun setupListeners() {
    bottom_navigation.setOnNavigationItemReselectedListener { /* Empty listener to block reselection */ }
    bottom_navigation.setOnNavigationItemSelectedListener { onBottomMenuItemSelected(it) }
  }

  private fun onBottomMenuItemSelected(menuItem: MenuItem): Boolean {
    when (menuItem.itemId) {
      R.id.menu_item_rates -> changeFragment(RatesFragment.create(), RatesFragment.TAG)
      R.id.menu_converter -> changeFragment(ConverterFragment.create(), ConverterFragment.TAG)
      else -> return false
    }
    return true
  }

  private fun changeFragment(fragment: Fragment, tag: String) {
    supportFragmentManager.beginTransaction().apply {
      replace(R.id.container, fragment, tag)
    }.commit()
  }

}
