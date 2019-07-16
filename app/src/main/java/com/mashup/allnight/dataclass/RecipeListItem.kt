package com.mashup.allnight.dataclass

import java.io.Serializable

class RecipeListItem(
    val imageUrl: String,
    val title: String,
    var scraped: Boolean = false,
    val id: String
):Serializable