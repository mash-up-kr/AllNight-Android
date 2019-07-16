package com.mashup.allnight.dataclass

class RecipeListItem(
    val imageUrl: String,
    val title: String,
    var scraped: Boolean = false,
    val id: String,
    val alcoholic: String
)