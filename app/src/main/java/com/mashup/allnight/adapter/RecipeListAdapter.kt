package com.mashup.allnight.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.DetailActivity
import com.mashup.allnight.DetailActivity.Companion.COCKTAIL_ID_KEY
import com.mashup.allnight.IScrappedRecipeListRequestListener
import com.mashup.allnight.R
import com.mashup.allnight.dataclass.RecipeListItem
import com.mashup.allnight.viewholder.RecipeListViewHolder
import kotlinx.android.synthetic.main.recipe_single_item.view.*


class RecipeListAdapter(private var itemList: ArrayList<RecipeListItem>,
                        private val scrapListListener: IScrappedRecipeListRequestListener): RecyclerView.Adapter<RecipeListViewHolder>() {

    var isSingleItemViewMode = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            if (viewType == 0) R.layout.recipe_single_item else R.layout.recipe_multiple_item,
            parent,false)

        return RecipeListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder:RecipeListViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(COCKTAIL_ID_KEY, itemList[position].id)
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.BtnScrap.setOnCheckedChangeListener{ _, isChecked ->
            itemList[position].scraped = isChecked

            if(isChecked)
                scrapListListener.addRecipeToScrapList(itemList[position])
            else
                scrapListListener.removeRecipeFromScrapList(itemList[position])
        }

        holder.bind(itemList[position])
    }

    fun setData(list: ArrayList<RecipeListItem>) {
        itemList = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (isSingleItemViewMode) 0 else 1
    }
}