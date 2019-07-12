package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.allnight.adapter.RecipeListAdapter
import com.mashup.allnight.customview.RecipeListFilterDialog
import kotlinx.android.synthetic.main.mix_recipe_multiple.*
import kotlinx.android.synthetic.main.mix_recipe_single.*
import kotlinx.android.synthetic.main.mix_recipe_single.BtnFilter
import kotlinx.android.synthetic.main.mix_recipe_single.BtnToHome_
import kotlinx.android.synthetic.main.mix_recipe_single.cardView_

class recipeSingleActivity : AppCompatActivity(),IRecipeListFilterModifiedListener {

    var alcoholMode : RecipeListFilterDialog.Companion.ALCOHOL = RecipeListFilterDialog.Companion.ALCOHOL.ALL
    var ingrdCount = 3

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
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        BtnFilter.setOnClickListener {
            val dialog = RecipeListFilterDialog(this, R.style.DialogFromBottom, this)
            dialog.setCheckState(alcoholMode, ingrdCount)
            dialog.show()
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

    override fun onFilterModified(alcohol: RecipeListFilterDialog.Companion.ALCOHOL, ingrdCount: Int) {
        this.alcoholMode = alcohol
        this.ingrdCount = ingrdCount
        //Todo : Not implemented. 필터 적용 후 새로 고치는 로직 필요
    }

}
