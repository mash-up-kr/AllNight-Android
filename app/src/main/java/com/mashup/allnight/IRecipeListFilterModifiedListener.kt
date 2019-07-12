package com.mashup.allnight

import com.mashup.allnight.customview.RecipeListFilterDialog

interface IRecipeListFilterModifiedListener {
    fun onFilterModified(alcohol: RecipeListFilterDialog.Companion.ALCOHOL, ingrdCount: Int)
}