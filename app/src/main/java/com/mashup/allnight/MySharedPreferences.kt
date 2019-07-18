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
        var items: ArrayList<RecipeListItem>
        if(json.isEmpty()){
            items = arrayListOf()
        }
        else{
            val type = object : TypeToken<ArrayList<RecipeListItem>>() {}.type
            items = gson.fromJson(json, type)
        }
        for(item: RecipeListItem in items)
            item.scraped = true

        return items
    }

    fun setScrappedRecipeListFromPref(values: ArrayList<RecipeListItem>){
        var gson = Gson()
        var json: String = gson.toJson(values)
        editor.putString(PREF_KEY_MY_SCRAP, json)
        editor.commit()
    }
}