package com.bobowiec.revolut_app.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bobowiec.revolut_app.R
import com.bobowiec.revolut_app.extensions.show
import com.bobowiec.revolut_app.ui.base.BaseFragment
import com.bobowiec.revolut_app.ui.converter.ConverterFragment
import com.bobowiec.revolut_app.ui.rates.RatesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupToolbar()
    setupListeners()

    changeFragment(RatesFragment.create())
  }

  private fun setupToolbar() {
    setSupportActionBar(toolbar)
  }

  private fun setupListeners() {
    bottomNavigation.setOnNavigationItemReselectedListener { /* Empty listener to block reselection */ }
    bottomNavigation.setOnNavigationItemSelectedListener { onBottomMenuItemSelected(it) }
  }

  private fun onBottomMenuItemSelected(menuItem: MenuItem): Boolean {
    when (menuItem.itemId) {
      R.id.menu_item_rates -> {
        changeFragment(RatesFragment.create())
        toolbar.show()
      }
      R.id.menu_converter -> {
        changeFragment(ConverterFragment.create())
        toolbar.show()
      }
      else -> return false
    }
    return true
  }

  private fun changeFragment(fragment: BaseFragment) {
    supportFragmentManager.beginTransaction().apply {
      replace(R.id.container, fragment, fragment.getFragmentTag())
    }.commit()
    title = getString(fragment.getTitle())
  }

}
