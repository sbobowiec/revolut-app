package com.bobowiec.revolut_app.ui.rates.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bobowiec.revolut_app.R
import com.bobowiec.revolut_app.data.model.Currency
import com.bobowiec.revolut_app.data.model.Rate
import com.bobowiec.revolut_app.extensions.inflate
import com.bobowiec.revolut_app.extensions.roundedValue
import com.bobowiec.revolut_app.ui.common.OnRateClickListener

import kotlinx.android.synthetic.main.view_rate_item.view.*

class RatesAdapter(
    private val onRateClickListener: OnRateClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var items: List<Rate> = ArrayList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    holder as ViewHolder
    holder.bind(items[position], position == 0)
  }

  override fun getItemCount() = items.count()

  fun addRates(rates: List<Rate>) {
    items = rates
    notifyDataSetChanged()
  }

  inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
      parent.inflate(R.layout.view_rate_item)) {

    fun bind(rate: Rate, isBase: Boolean) = with(itemView) {
      val currency = Currency.valueOf(rate.symbol)
      flag_icon.setImageResource(currency.flagIconRes)
      currency_name.text = currency.fullName

      rate_symbol.text = rate.symbol
      rate_value.text = rate.roundedValue(
          if (isBase) Rate.BASE_RATE_DECIMAL_PLACES else Rate.EXCHANGE_RATE_DECIMAL_PLACES
      )

      setOnClickListener { onRateClickListener(rate) }
    }

  }

}