package com.mashup.allnight

import com.mashup.allnight.dataclass.RecipeListItem

object ScrapManager {

    fun addRecipeToScrapList(recipeListItem: RecipeListItem) {
        val scrappedList = App.prefs.getScrappedRecipeListFromPref()
        // check contains
        if(isContained(recipeListItem)) return

        scrappedList.add(recipeListItem)
        App.prefs.setScrappedRecipeListFromPref(scrappedList)
    }

    fun removeRecipeFromScrapList(recipeListItem: RecipeListItem) {
        val scrappedList = App.prefs.getScrappedRecipeListFromPref()
        // check contains
        val iterator = scrappedList.iterator()
        while (iterator.hasNext()) {
            val id = iterator.next().id
            if (recipeListItem.id == id) iterator.remove()
        }
        App.prefs.setScrappedRecipeListFromPref(scrappedList)
    }

    fun isContained(id: String): Boolean {
        val scrappedList = App.prefs.getScrappedRecipeListFromPref()
        // check contains
        for(item: RecipeListItem in scrappedList) {
            if (item.id == id) return true
        }
        return false
    }

    fun isContained(recipeListItem: RecipeListItem): Boolean {
        return isContained(recipeListItem.id)
    }
}