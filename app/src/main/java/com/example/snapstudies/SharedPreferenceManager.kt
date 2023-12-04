package com.example.snapstudies

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPreferenceManager (context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveData(key: String, data: Any) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(data)
        editor.putString(key, json)
        editor.apply()
    }

    fun getData(key: String, clazz: Class<UserData>): UserData? {
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            gson.fromJson(json, clazz)
        } else {
            null
        }
    }

    /*fun getData(key: String, clazz: Class<*>): HashMap<String, String>? {
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            gson.fromJson(json, clazz) as? HashMap<String, String>
        } else {
            null
        }
    }*/
}