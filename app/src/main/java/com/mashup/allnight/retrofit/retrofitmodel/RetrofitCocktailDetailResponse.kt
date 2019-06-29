package com.mashup.allnight.retrofit.retrofitmodel

import com.google.gson.annotations.SerializedName

class RetrofitCocktailDetailResponse {

    @SerializedName("drinkName")
    val drinkName = ""

    @SerializedName("category")
    val category = ""

    @SerializedName("alcoholic")
    val alcoHolic = ""

    @SerializedName("glass")
    val glass = ""

    @SerializedName("instructions")
    val instruction = ""

    @SerializedName("drinkThumb")
    val thumbnailUrl = ""

    @SerializedName("EnDrinkName")
    val drinkNameEng = ""

    @SerializedName("ingredient")
    val ingredients = arrayListOf<String>()

    @SerializedName("measure")
    val measures = arrayListOf<String>()

    @SerializedName("EnIngredient")
    val ingredientsEng = arrayListOf<String>()

    /*
    "drinkName": "크림 드 멘체",
    "category": "수제 리큐르",
    "alcoholic": "Alcoholic",
    "glass": "콜린스 글래스",
    "instructions": "설탕과 물을 끓여서 10 분 동안 끓인다. 시원한. 나머지 성분을 넣고 저어줍니다. 덮개를 씌우고 1 달 동안 숙성시킨다.",
    "drinkThumb": "https://www.thecocktaildb.com/images/media/drink/yxswtp1441253918.jpg",
    "ingredient01": "설탕",
    "ingredient02": "물",
    "ingredient03": "알콜 알콜",
    "ingredient04": "박하 추출물",
    "ingredient05": "식용 색소",
    "measure01": "8 cups ",
    "measure02": "6 cups ",
    "measure03": "1 pint ",
    "measure04": "1 oz pure ",
    "measure05": "1 tblsp green ",
    "EnDrinkName": "Creme de Menthe",
    "EnIngredient01": "Sugar",
    "EnIngredient02": "Water",
    "EnIngredient03": "Grain alcohol",
    "EnIngredient04": "Peppermint extract",
    "EnIngredient05": "Food coloring"
     */
}