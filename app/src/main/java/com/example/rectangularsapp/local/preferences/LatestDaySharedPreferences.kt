package com.example.rectangularsapp.local.preferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class LatestDaySharedPreferences @Inject constructor(
    private val context: Context
) {

    private val sharedPreferences: SharedPreferences
        get() = context.getSharedPreferences("LatestDay", Context.MODE_PRIVATE)

    private var latestDayLocal by SharedPreferencesLong(
        key = { KEY_LATEST_DAY },
        defaultValue = -1L,
        sharedPreferences = sharedPreferences
    )

    fun getLatestDay(): Long = latestDayLocal

    fun setLatestDay(newLatestDay: Long) {
        latestDayLocal = newLatestDay
    }

    inner class SharedPreferencesLong(
        private val defaultValue: Long = 0,
        private val key: (KProperty<*>) -> String = KProperty<*>::name,
        private val sharedPreferences: SharedPreferences
    ) : ReadWriteProperty<Any, Long> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Long {
            return sharedPreferences.getLong(key(property), defaultValue)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
            sharedPreferences.edit().putLong(key(property), value).apply()
        }
    }

    companion object {
        private const val KEY_LATEST_DAY = "latest_day"
    }
}