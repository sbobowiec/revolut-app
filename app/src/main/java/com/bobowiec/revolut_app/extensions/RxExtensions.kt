package com.bobowiec.revolut_app.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addTo(disposables: CompositeDisposable): Disposable = apply { disposables.add(this) }