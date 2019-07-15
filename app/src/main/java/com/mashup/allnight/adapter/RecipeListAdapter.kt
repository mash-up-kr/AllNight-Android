package com.mashup.allnight.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.DetailActivity
import com.mashup.allnight.DetailActivity.Companion.COCKTAIL_ID_KEY
import com.mashup.allnight.R
import com.mashup.allnight.dataclass.RecipeListItem
import com.mashup.allnight.viewholder.RecipeListViewHolder



class RecipeListAdapter(private var itemList: ArrayList<RecipeListItem>): RecyclerView.Adapter<RecipeListViewHolder>() {

    var itemHeight = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_single_item, parent, false)

        return RecipeListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder:RecipeListViewHolder, position: Int) {
        holder.bind(itemList[position], itemHeight)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(COCKTAIL_ID_KEY, itemList[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun setData(list: ArrayList<RecipeListItem>) {
        itemList = list
        notifyDataSetChanged()
    }
}