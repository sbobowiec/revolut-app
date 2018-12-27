package com.bobowiec.revolut_app.extensions

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

fun View.show() {
  visibility = View.VISIBLE
}

fun View.hide() {
  visibility = View.GONE
}

fun MenuItem.show() {
  isVisible = true
}

fun MenuItem.hide() {
  isVisible = false
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}