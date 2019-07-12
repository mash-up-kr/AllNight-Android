package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.allnight.adapter.RecipeListAdapter
import kotlinx.android.synthetic.main.mix_recipe_single.*

class recipeSingleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mix_recipe_single)

        BtnToHome_.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(intent)
        }

        BtnViewMultiple.setOnClickListener{
            val intent = Intent(this, recipeMultipleActivity::class.java)
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
        val adapter = RecipeListAdapter(list)
        cardView_.adapter = adapter
        cardView_.layoutManager = LinearLayoutManager(this)
    }
}
