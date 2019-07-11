package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.allnight.adapter.BucketItemAdapter
import com.mashup.allnight.adapter.SearchResultAdapter
import com.mashup.allnight.dataclass.DataList
import kotlinx.android.synthetic.main.activity_bucket.*
import kotlinx.android.synthetic.main.activity_drink_kind.*

class BucketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bucket)

        val adapter = BucketItemAdapter(initItemData())
        bucket_item_recyclerview.adapter = adapter
        bucket_item_recyclerview.layoutManager = LinearLayoutManager(this)

        back_button_bucket.setOnClickListener{
            val intent = Intent(this,DrinkKindActivity::class.java)
            finish()
        }

        next_button_bucket.setOnClickListener{
            val intent = Intent(this, recipeSingleActivity::class.java)
            startActivity(intent)
        }
    }



    fun initItemData(): MutableList<DataList> {

        val list = intent.getSerializableExtra("checked") as ArrayList<DataList>?
        return if (list is MutableList<DataList>)
            list
        else arrayListOf()
    }
}
