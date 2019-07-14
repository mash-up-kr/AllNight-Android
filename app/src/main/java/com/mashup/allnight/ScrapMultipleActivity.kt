package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.mashup.allnight.adapter.RecipeListAdapter
import com.mashup.allnight.dataclass.RecipeListItem
import kotlinx.android.synthetic.main.activity_scrap_multiple.*

class ScrapMultipleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrap_multiple)

        BtnToBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        BtnViewSingle.setOnClickListener{
            val intent = Intent(this, ScrapSingleActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        init()
    }

    fun init() {
        val list = ArrayList<RecipeListItem>()
        list.add(RecipeListItem("x", "test1", false, "1"))
        list.add(RecipeListItem("x", "test2", false, "1"))
        list.add(RecipeListItem("x", "test3", false, "1"))
        list.add(RecipeListItem("x", "test4", false, "1"))
        list.add(RecipeListItem("x", "test5", false, "1"))
        list.add(RecipeListItem("x", "test6", false, "1"))
        val adapter = RecipeListAdapter(list)
        cardView_.adapter = adapter
        cardView_.layoutManager = GridLayoutManager(this,2)
    }
}
