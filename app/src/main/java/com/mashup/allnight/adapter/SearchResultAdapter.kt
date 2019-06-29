package com.mashup.allnight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.R
import com.mashup.allnight.dataclass.DataList
import com.mashup.allnight.viewholder.SearchResultViewHolder

class SearchResultAdapter (private var itemList : MutableList<DataList>):RecyclerView.Adapter<SearchResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val viewHolder: SearchResultViewHolder

        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false)
        viewHolder = SearchResultViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun getItem(position: Int):DataList{
        return itemList[position]
    }
}