package com.mashup.allnight.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.dataclass.DrinkSpecific
import kotlinx.android.synthetic.main.search_result_item.view.*
import kotlinx.android.synthetic.main.activity_detail.view.*

class DrinkSpecificViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: DrinkSpecific) {
        itemView.intro_text.text = data.TITLE
        itemView.intro_passage.text = data.INTRO
        itemView.glass_passage.text = data.GLASS
        // need_listView 추가 해야함
        itemView.instruction_passage.text = data.INSTRUCTION

    }
}