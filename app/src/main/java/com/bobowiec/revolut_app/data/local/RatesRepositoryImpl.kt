package com.bobowiec.revolut_app.data.local

import android.content.Context
import android.content.SharedPreferences
import com.bobowiec.revolut_app.data.model.Rate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class RatesRepositoryImpl(
    context: Context,
    private val gson: Gson,
    private var memoryCache: List<Rate> = listOf()
) : RatesRepository {

  private var preferences: SharedPreferences

  init {
    preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
  }

  override fun saveRates(rates: List<Rate>) {
    memoryCache = rates
  }

  override fun getRates(): List<Rate> {
    if (memoryCache.isEmpty()) {
      memoryCache = getRatesFromPrefs()
    }
    return memoryCache
  }

  override fun syncRates() {
    saveRatesToPrefs(memoryCache)
  }

  private fun getRatesFromPrefs(): List<Rate> {
    val ratesJSON = preferences.getString(KEY_SAVED_RATES, null) ?: return ArrayList()
    val ratesType = object : TypeToken<List<Rate>>(){}.type
    return gson.fromJson<List<Rate>>(ratesJSON, ratesType)
  }

  private fun saveRatesToPrefs(rates: List<Rate>) {
    preferences.edit().putString(KEY_SAVED_RATES, gson.toJson(rates)).apply()
  }

  companion object {
    private const val PREFERENCES_FILE_NAME = "com.bobowiec.revolut_app.PREFERENCES"
    private const val KEY_SAVED_RATES = "key_saved_rates"
  }

}