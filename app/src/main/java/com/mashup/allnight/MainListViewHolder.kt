package com.mashup.allnight

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recipe_single_item.view.*

abstract class MainListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    abstract fun bind(mainListItem: MainListItem)

    class MainTodayCocktailViewHolder(itemView: View): MainListViewHolder(itemView){
        override fun bind(mainListItem: MainListItem){
            itemView.title.text = mainListItem.title
        }
    }

}