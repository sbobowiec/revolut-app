package com.bobowiec.revolut_app.ui.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bobowiec.revolut_app.R
import com.bobowiec.revolut_app.extensions.getAppComponent
import com.bobowiec.revolut_app.ui.base.BaseFragment
import javax.inject.Inject

class ConverterFragment : BaseFragment(), ConverterView {

  @Inject
  lateinit var presenter: ConverterPresenter

  override fun getTitle() = R.string.title_fragment_converter

  override fun getFragmentTag() = TAG

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    context?.getAppComponent()?.inject(this)
    presenter.bindView(this)
  }

  override fun onDestroy() {
    presenter.unbindView()
    super.onDestroy()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_converter, container, false)
  }

  companion object {

    private const val TAG = "ConverterFragment"

    fun create() = ConverterFragment()

  }

}