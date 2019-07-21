package com.mashup.allnight.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.dataclass.MainListItem
import kotlinx.android.synthetic.main.recipe_single_item.view.*

abstract class MainListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(mainListItem: MainListItem)

    class MainRecommendViewHolder(itemView: View) : MainListViewHolder(itemView) {
        override fun bind(mainListItem: MainListItem) {
//            itemView.title.text = mainListItem.title
//            itemView.ivCocktail.imgUrl = mainListItem.imageUrl
//            itemView.tvAlcoholic.text = mainListItem.alcoholic
//            itemView.BtnScrap.isChecked = mainListItem.scraped
        }
    }

    class MainTodayCocktailViewHolder(itemView: View) : MainListViewHolder(itemView) {
        override fun bind(mainListItem: MainListItem) {
            itemView.title.text = mainListItem.title
            itemView.ivCocktail.imgUrl = mainListItem.imageUrl
            itemView.tvAlcoholic.text = mainListItem.alcoholic
            itemView.BtnScrap.isChecked = mainListItem.scraped
        }

    }

    companion object {
        const val TYPE_TODAY = 0
        const val TYPE_RECOMMEND = 1
    }
}