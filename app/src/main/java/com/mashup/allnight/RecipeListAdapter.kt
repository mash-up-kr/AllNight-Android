package com.mashup.allnight

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecipeListAdapter(private var itemList: ArrayList<MainListItem>): RecyclerView.Adapter<MainListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val viewHolder: MainListViewHolder

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_single_item, parent, false)
        viewHolder = MainListViewHolder.MainTodayCocktailViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}