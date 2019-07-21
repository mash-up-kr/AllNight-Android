package com.mashup.allnight.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.DetailActivity
import com.mashup.allnight.dataclass.MainListItem
import com.mashup.allnight.R
import com.mashup.allnight.viewholder.MainListViewHolder
import kotlinx.android.synthetic.main.main_list_today_item.view.*

class MainListAdapter(private var itemList: ArrayList<MainListItem>) : RecyclerView.Adapter<MainListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val viewHolder: MainListViewHolder

        when (viewType) {
            MainListViewHolder.TYPE_RECOMMEND -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.main_list_recommand_item, parent, false)
                viewHolder = MainListViewHolder.MainRecommendViewHolder(view)
            }
            MainListViewHolder.TYPE_TODAY -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.main_list_today_item, parent, false)
                viewHolder = MainListViewHolder.MainTodayCocktailViewHolder(view)
            }

            else -> { //default
                val view = LayoutInflater.from(parent.context).inflate(R.layout.main_list_recommand_item, parent, false)
                viewHolder = MainListViewHolder.MainRecommendViewHolder(view)
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.COCKTAIL_ID_KEY, itemList[position].id)
            holder.itemView.context.startActivity(intent)
        }
        holder.itemView.BtnScrap?.setOnCheckedChangeListener { p0, checked ->
            itemList[position].scraped = checked
        }
        holder.bind(itemList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position].viewType
    }

    fun setData(list: ArrayList<MainListItem>) {
        itemList = list
        notifyDataSetChanged()
    }
}