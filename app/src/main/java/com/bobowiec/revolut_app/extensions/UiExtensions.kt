package com.bobowiec.revolut_app.extensions

import android.support.annotation.ColorRes
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun View.show() {
  visibility = View.VISIBLE
}

fun View.hide() {
  visibility = View.GONE
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT, @ColorRes backgroundColorRes: Int = 0) {
  Snackbar.make(this, message, duration).run {
    if (backgroundColorRes != 0) view.setBackgroundResource(backgroundColorRes)
    show()
  }
}