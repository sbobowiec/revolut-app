package com.bobowiec.revolut_app.ui.rates.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import com.bobowiec.revolut_app.R
import com.bobowiec.revolut_app.data.model.Currency
import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.extensions.inflate

import kotlinx.android.synthetic.main.view_rate_item.view.*
import com.bobowiec.revolut_app.data.remote.RatesApiConfig
import com.bobowiec.revolut_app.util.convert.RatesConverter

typealias OnRateClickListener = (Rate) -> Unit

class RatesAdapter(
    private val ratesConverter: RatesConverter = RatesConverter(),
    private val onRateClickListener: OnRateClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  var items: List<Rate> = ArrayList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    holder as ViewHolder
    holder.rateInputValueChangedListener.rateItemPosition = position
    holder.bind(items[position])
  }

  override fun getItemCount() = items.count()

  fun refresh(rates: List<Rate>) {
    val baseRateChanged = ratesConverter.baseRateChanged(rates)
    items = ratesConverter.convert(rates)

    if (baseRateChanged) {
      notifyDataSetChanged()
    } else {
      notifyItemRangeChanged(1, items.size - 1)
    }
  }

  inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
      parent.inflate(R.layout.view_rate_item)) {

    val rateInputValueChangedListener = RateInputValueChangedListener()

    fun bind(rate: Rate) {
      bindCommonValues(rate)
      if (isBase(rate)) bindBaseRate() else bindExchangeRate()
    }

    private fun isBase(rate: Rate) = RatesApiConfig.BASE_PARAM_VALUE == rate.symbol

    private fun bindCommonValues(rate: Rate) = with(itemView) {
      val currency = Currency.valueOf(rate.symbol)

      flag_icon.setImageResource(currency.flagIconRes)
      currency_name.text = currency.fullName
      rate_symbol.text = rate.symbol
      rate_value.setText(rate.value.toPlainString())

      setOnClickListener { onRateClickListener(rate) }
    }

    private fun bindBaseRate() = with(itemView) {
      rate_value.isEnabled = true
      rate_value.background.colorFilter = PorterDuffColorFilter(
          ContextCompat.getColor(context, R.color.colorAccent),
          PorterDuff.Mode.SRC_IN
      )
      rate_value.addTextChangedListener(rateInputValueChangedListener)
    }

    private fun bindExchangeRate() = with(itemView) {
      rate_value.isEnabled = false
      rate_value.background.colorFilter = PorterDuffColorFilter(
          Color.BLACK,
          PorterDuff.Mode.SRC_IN
      )
    }
  }

  inner class RateInputValueChangedListener(var rateItemPosition: Int = 0) : TextWatcher {
    override fun afterTextChanged(s: Editable?) { }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
      if (rateItemPosition != 0) return

      val newBaseRate = text.toString().toBigDecimalOrNull() ?: Rate.BASE_RATE_DEFAULT_VALUE
      ratesConverter.updateBaseRate(newBaseRate)
    }
  }

}