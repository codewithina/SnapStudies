package com.example.snapstudies

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun <T> saveData(key: String, data: T) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(data)
        editor.putString(key, json)
        editor.apply()
    }

    fun <T> getData(key: String, clazz: Class<T>): T? {
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            gson.fromJson(json, clazz)
        } else {
            null
        }
    }
}
