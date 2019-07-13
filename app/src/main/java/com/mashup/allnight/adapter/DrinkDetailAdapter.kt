package com.mashup.allnight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.allnight.R
import com.mashup.allnight.dataclass.DrinkNeedSpecific
import kotlinx.android.synthetic.main.detail_need_item.view.*

class DrinkDetailAdapter(private var items: ArrayList<DrinkNeedSpecific>): RecyclerView.Adapter<DrinkDetailAdapter.DrinkViewHolder>(){
    //items.(DrinkNeedSpecific("사이다",3),DrinkNeedSpecific("물",1)


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = DrinkViewHolder(parent)
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int){
        items[position].let{item ->
            with(holder) {
                ingredient.text = item.ingredient
                size.text = item.size.toString() + "oz"

            }
        }
    }

    inner class DrinkViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.detail_need_item, parent,false)){
        var ingredient = itemView.ingredient
        var size = itemView.size

    }

}
