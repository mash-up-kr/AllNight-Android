package com.mashup.allnight.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.dataclass.DataList
import kotlinx.android.synthetic.main.search_result_item.view.*

class BucketItemViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: DataList) {
        itemView.search_item_text.text = data.name
    }
}