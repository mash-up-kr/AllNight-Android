package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.allnight.adapter.SearchResultAdapter
import com.mashup.allnight.dataclass.DataList
import kotlinx.android.synthetic.main.activity_drink_kind.*

class DrinkKindActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_kind)

        val adapter = SearchResultAdapter(initItemData())
        search_item_recyclerview.adapter = adapter
        search_item_recyclerview.layoutManager = LinearLayoutManager(this)

        var checkedList: ArrayList<DataList> = arrayListOf()
        bucket_button.setOnClickListener{
            for(i in 0 until adapter.getItemCount()){
                if(adapter.getItem(i).checked){
                    checkedList.add(adapter.getItem(i))
                }
            }

            val nextIntent = Intent(this, BucketActivity::class.java)
            nextIntent.putExtra("checked", checkedList)
            startActivity(nextIntent)
        }
    }

    fun initItemData(): MutableList<DataList> {

        var itemList: MutableList<DataList> = arrayListOf()


        var item1 = DataList("SIGNAL", false)
        var item2 = DataList("I LUV IT", false)
        var item3 = DataList("All I Wanna Do", false)
        var item4 = DataList("Merry Me", false)
        var item5 = DataList("A", false)
        var item6 = DataList("Hi", false)
        var item7 = DataList("My Name", false)
        var item8 = DataList("You", false)
        var item9 = DataList("Home", false)
        var item10 = DataList("Hello", false)

        itemList.add(item1)
        itemList.add(item2)
        itemList.add(item3)
        itemList.add(item4)
        itemList.add(item5)
        itemList.add(item6)
        itemList.add(item7)
        itemList.add(item8)
        itemList.add(item9)
        itemList.add(item10)

        return itemList
    }
}
