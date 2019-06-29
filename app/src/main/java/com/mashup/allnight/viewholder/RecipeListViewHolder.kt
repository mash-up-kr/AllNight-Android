package com.mashup.allnight.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.MainListItem
import kotlinx.android.synthetic.main.recipe_single_item.view.*

abstract class RecipeListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    abstract fun bind(mainListItem: MainListItem)

    class CocktailViewHolder(itemView: View): RecipeListViewHolder(itemView){
        override fun bind(mainListItem: MainListItem){
            itemView.title.text = mainListItem.title
        }
    }

}