package com.bobowiec.revolut_app.ui.base

import io.reactivex.disposables.CompositeDisposable

/**
 * Base MVP presenter.
 */
abstract class BasePresenter<View : MvpView> {

  var view: View? = null
  var disposables: CompositeDisposable = CompositeDisposable()

  fun bindView(mvpView: View) {
    this.view = mvpView
  }

  fun unbindView() {
    this.disposables.clear()
    this.view = null
  }

}