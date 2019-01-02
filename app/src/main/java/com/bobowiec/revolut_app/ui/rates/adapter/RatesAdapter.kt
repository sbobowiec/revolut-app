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
import com.bobowiec.revolut_app.extensions.roundedValue
import com.bobowiec.revolut_app.util.convert.RatesConverter
import com.bobowiec.revolut_app.util.convert.RefreshRatesListener

import kotlinx.android.synthetic.main.view_rate_item.view.*
import java.math.BigDecimal

typealias OnRateClickListener = (Rate) -> Unit

class RatesAdapter(
    private val onRateClickListener: OnRateClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  var items: List<Rate> = ArrayList()

  private val ratesConverter = RatesConverter(object: RefreshRatesListener {
    override fun refreshAll() {
      notifyDataSetChanged()
    }

    override fun refreshExchangeRates() {
      notifyItemRangeChanged(1, itemCount - 1)
    }
  })

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    holder as ViewHolder
    holder.rateInputValueChangedListener.rateItemPosition = position
    holder.bind(items[position], position == 0)
  }

  override fun getItemCount() = items.count()

  fun refresh(rates: List<Rate>) {
    items = rates
    ratesConverter.convert(items)
  }

  inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
      parent.inflate(R.layout.view_rate_item)) {

    val rateInputValueChangedListener = RateInputValueChangedListener()

    fun bind(rate: Rate, isBase: Boolean) {
      bindCommonValues(rate)
      if (isBase) bindBaseRate(rate) else bindExchangeRate(rate)
    }

    private fun bindCommonValues(rate: Rate) = with(itemView) {
      val currency = Currency.valueOf(rate.symbol)

      flag_icon.setImageResource(currency.flagIconRes)
      currency_name.text = currency.fullName
      rate_symbol.text = rate.symbol

      setOnClickListener { onRateClickListener(rate) }
    }

    private fun bindBaseRate(rate: Rate) = with(itemView) {
      rate_value.setText(rate.roundedValue(Rate.BASE_RATE_DECIMAL_PLACES))
      rate_value.isEnabled = true
      rate_value.background.colorFilter = PorterDuffColorFilter(
          ContextCompat.getColor(context, R.color.colorAccent),
          PorterDuff.Mode.SRC_IN
      )
      rate_value.addTextChangedListener(rateInputValueChangedListener)
    }

    private fun bindExchangeRate(rate: Rate) = with(itemView) {
      rate_value.setText(rate.roundedValue(Rate.EXCHANGE_RATE_DECIMAL_PLACES))
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
      ratesConverter.baseRateValue = text.toString().toDoubleOrNull() ?: 1.0
      ratesConverter.baseRateValue = BigDecimal(ratesConverter.baseRateValue)
          .setScale(2, BigDecimal.ROUND_HALF_UP)
          .toDouble()
    }
  }

}