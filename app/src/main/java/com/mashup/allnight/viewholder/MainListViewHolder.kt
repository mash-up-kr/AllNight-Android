package com.mashup.allnight.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.dataclass.MainListItem
import kotlinx.android.synthetic.main.main_list_recommand_item.view.*
import kotlinx.android.synthetic.main.main_list_today_item.view.*

abstract class MainListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(mainListItem: MainListItem)

    class MainRecommendViewHolder(itemView: View) : MainListViewHolder(itemView) {
        override fun bind(mainListItem: MainListItem) {

        }
    }

    class MainTodayCocktailViewHolder(itemView: View) : MainListViewHolder(itemView) {
        override fun bind(mainListItem: MainListItem) {
            itemView.scrap_button.setOnCheckedChangeListener { p0, p1 ->
                mainListItem.scraped = itemView.scrap_button.isChecked
            }
        }

    }

    companion object {
        const val TYPE_TODAY = 0
        const val TYPE_RECOMMEND = 1
    }
}