package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.allnight.adapter.RecipeListAdapter
import com.mashup.allnight.dataclass.RecipeListItem
import kotlinx.android.synthetic.main.activity_scrap_single.*

class ScrapSingleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrap_single)

        BtnToBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        BtnSwitchViewMode.setOnClickListener{
            val intent = Intent(this, ScrapMultipleActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        init()
    }

    fun init() {
        val list = ArrayList<RecipeListItem>()
        list.add(RecipeListItem("x", "test1"))
        list.add(RecipeListItem("x", "test2"))
        list.add(RecipeListItem("x", "test3"))
        list.add(RecipeListItem("x", "test4"))
        list.add(RecipeListItem("x", "test5"))
        list.add(RecipeListItem("x", "test6"))
        val adapter = RecipeListAdapter(list)
        cardView_.adapter = adapter
        cardView_.layoutManager = LinearLayoutManager(this)
    }
}
