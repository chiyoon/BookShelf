package com.example.bookshelf.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapTypeConverter {

    @TypeConverter
    fun fromString(value: String?): Map<String, String>? {
        return if (value == null) {
            null
        } else {
            val mapType = object : TypeToken<Map<String, String>>() {}.type
            Gson().fromJson(value, mapType)
        }
    }

    @TypeConverter
    fun fromMap(map: Map<String, String>?): String? {
        return Gson().toJson(map)
    }

}
