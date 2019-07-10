package com.mashup.allnight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.MainListItem
import com.mashup.allnight.R
import com.mashup.allnight.viewholder.RecipeListViewHolder


class RecipeListAdapter(private var itemList: ArrayList<MainListItem>): RecyclerView.Adapter<RecipeListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val viewHolder: RecipeListViewHolder

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_single_item, parent, false)
        viewHolder = RecipeListViewHolder.CocktailViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder:RecipeListViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}