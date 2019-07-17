package com.mashup.allnight

import com.mashup.allnight.dataclass.RecipeListItem

interface IScrappedRecipeListRequestListener {
    fun addRecipeToScrapList(recipeListItem: RecipeListItem)
    fun removeRecipeFromScrapList(recipeListItem: RecipeListItem)
}