package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.allnight.adapter.RecipeListAdapter
import com.mashup.allnight.adapter.RecipeMultipleListAdapter
import kotlinx.android.synthetic.main.mix_recipe_single.*

class recipeMultipleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mix_recipe_multiple)

        BtnToHome.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
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
        cardView.adapter = adapter
        cardView.layoutManager = GridLayoutManager(this,2)
    }
}