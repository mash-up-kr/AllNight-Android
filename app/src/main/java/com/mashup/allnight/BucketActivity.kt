package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.allnight.adapter.BucketItemAdapter
import com.mashup.allnight.dataclass.DataList
import kotlinx.android.synthetic.main.activity_bucket.*
import kotlinx.android.synthetic.main.bucket_item.*

class BucketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bucket)

        val adapter = BucketItemAdapter(initItemData())

        bucket_item_recyclerview.adapter = adapter
        bucket_item_recyclerview.layoutManager = LinearLayoutManager(this)

        back_button_bucket.setOnClickListener{
            finish()
        }

        next_button_bucket.setOnClickListener{
            val items: ArrayList<String> = arrayListOf()
            for(i in 0 until adapter.getItemList().size) {
                items.add(adapter.getItemList()[i].name)
            }

            val intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("item", items)
            intent.putExtra(RecipeListActivity.RECIPE_LIST_TYPE_KEY, RecipeListActivity.RECIPE_LIST_TYPE_SEARCH)
            startActivity(intent)
        }

        bucket_search_button.setOnClickListener{
            val items: ArrayList<String> = arrayListOf()
            for(i in 0 until adapter.getItemList().size) {
                items.add(adapter.getItemList()[i].name)
            }

            val intent = Intent(this, RecipeListActivity::class.java)
            intent.putExtra("item", items)
            intent.putExtra(RecipeListActivity.RECIPE_LIST_TYPE_KEY, RecipeListActivity.RECIPE_LIST_TYPE_SEARCH)
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
