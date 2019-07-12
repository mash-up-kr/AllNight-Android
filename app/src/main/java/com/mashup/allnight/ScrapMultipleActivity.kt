package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.mashup.allnight.adapter.RecipeMultipleListAdapter
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
            startActivity(intent)
        }

        init()
    }

    fun init() {
        val list = ArrayList<MainListItem>()
        list.add(MainListItem(0, "x", "test1"))
        list.add(MainListItem(0, "x", "test2"))
        list.add(MainListItem(0, "x", "test3"))
        list.add(MainListItem(0, "x", "test4"))
        list.add(MainListItem(0, "x", "test5"))
        list.add(MainListItem(0, "x", "test6"))
        val adapter = RecipeMultipleListAdapter(list)
        cardView_.adapter = adapter
        cardView_.layoutManager = GridLayoutManager(this,2)
    }
}
