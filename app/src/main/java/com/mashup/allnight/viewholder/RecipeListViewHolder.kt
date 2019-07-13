package com.mashup.allnight.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.dataclass.RecipeListItem
import kotlinx.android.synthetic.main.recipe_single_item.view.*

class RecipeListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

   fun bind(mainListItem: RecipeListItem){
            itemView.title.text = mainListItem.title
   }
}