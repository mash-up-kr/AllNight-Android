package com.mashup.allnight.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.dataclass.RecipeListItem
import kotlinx.android.synthetic.main.recipe_single_item.view.*

class RecipeListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

   fun bind(item: RecipeListItem){
       itemView.title.text = item.title
       itemView.ivCocktail.imgUrl = item.imageUrl
       itemView.tvAlcoholic.text = item.alcoholic
       itemView.BtnScrap.isChecked = item.scraped
   }
}