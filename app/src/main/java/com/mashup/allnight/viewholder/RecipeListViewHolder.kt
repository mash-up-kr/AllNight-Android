package com.mashup.allnight.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.dataclass.RecipeListItem
import kotlinx.android.synthetic.main.recipe_single_item.view.*

class RecipeListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

   fun bind(mainListItem: RecipeListItem, itemHeight: Int){
       itemView.title.text = mainListItem.title
       itemView.ivCocktail.imgUrl = mainListItem.imageUrl
       itemView.tvAlcoholic.text = mainListItem.alcoholic
       itemView.layoutParams.height = itemHeight
   }
}