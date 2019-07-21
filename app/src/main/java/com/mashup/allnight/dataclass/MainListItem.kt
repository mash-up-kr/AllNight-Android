package com.mashup.allnight.dataclass

data class MainListItem (
    val viewType: Int,
    val id: String,
    val imageUrl: String,
    val title: String,
    val alcoholic: String,
    var scraped: Boolean = false
)