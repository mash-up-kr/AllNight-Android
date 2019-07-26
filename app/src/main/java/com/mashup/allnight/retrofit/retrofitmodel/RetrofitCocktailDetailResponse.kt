package com.mashup.allnight.retrofit.retrofitmodel

import com.google.gson.annotations.SerializedName

class RetrofitCocktailDetailResponse {

    @SerializedName("drinkName")
    val drinkName = ""

    @SerializedName("enDrinkName")
    val drinkNameEng = ""

    @SerializedName("category")
    val category = ""

    @SerializedName("enCategory")
    val categoryEng = ""

    @SerializedName("alcoholic")
    val alcoHolic = ""

    @SerializedName("glass")
    val glass = ""

    @SerializedName("enGlass")
    val glassEng = ""

    @SerializedName("instructions")
    val instruction = ""

    @SerializedName("enInstructions")
    val instructionEng = ""

    @SerializedName("drinkThumb")
    val thumbnailUrl = ""

    @SerializedName("ingredientArray")
    val ingredients = arrayListOf<String>()

    @SerializedName("measureArray")
    val measure = arrayListOf<String>()

    @SerializedName("enIngredientArray")
    val ingredientsEng = arrayListOf<String>()

    @SerializedName("enMeasureArray")
    val measureEng = arrayListOf<String>()


}