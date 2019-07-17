package com.mashup.allnight

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mashup.allnight.dataclass.RecipeListItem

class MySharedPreferences(context: Context) {
    val PREFS_FILENAME = "prefs"
    val PREF_KEY_MY_SCRAP = "scrap"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    val editor: SharedPreferences.Editor = prefs.edit()

    fun getScrappedRecipeListFromPref(): ArrayList<RecipeListItem>{
        var gson = Gson()
        var json: String = prefs.getString(PREF_KEY_MY_SCRAP, "")!!
        val type = object : TypeToken<ArrayList<RecipeListItem>>() {}.type
        var items: ArrayList<RecipeListItem> = gson.fromJson(json, type)

        return items
    }

    fun setScrappedRecipeListFromPref(values: ArrayList<RecipeListItem>){
        var gson = Gson()
        var json: String = gson.toJson(values)
        editor.putString(PREF_KEY_MY_SCRAP, json)
        editor.commit()
    }
}