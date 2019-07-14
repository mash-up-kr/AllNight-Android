package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.mashup.allnight.adapter.RecipeListAdapter
import com.mashup.allnight.customview.RecipeListFilterDialog
import com.mashup.allnight.dataclass.RecipeListItem
import kotlinx.android.synthetic.main.mix_recipe_single.*
import kotlinx.android.synthetic.main.mix_recipe_single.BtnFilter
import kotlinx.android.synthetic.main.mix_recipe_single.BtnToHome_
import kotlinx.android.synthetic.main.mix_recipe_single.cardView_

class RecipeListActivity : AppCompatActivity(),IRecipeListFilterModifiedListener {

    private var alcoholMode : RecipeListFilterDialog.Companion.ALCOHOL = RecipeListFilterDialog.Companion.ALCOHOL.ALL
    private var ingrdCount = 3

    private var isSingleViewMode = true
    private val gridLayoutManager = GridLayoutManager(this, 2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mix_recipe_single)

        BtnToHome_.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        BtnSwitchViewMode.setOnClickListener{
            isSingleViewMode = !isSingleViewMode
            passItemHeightToAdapter()
        }

        BtnFilter.setOnClickListener {
            val dialog = RecipeListFilterDialog(this, R.style.DialogFromBottom, this)
            dialog.setCheckState(alcoholMode, ingrdCount)
            dialog.show()
        }

        init()
    }

    private fun init() {
        val list = ArrayList<RecipeListItem>()
        list.add(RecipeListItem("x", "test1", false, "1"))
        list.add(RecipeListItem("x", "test2", false, "1"))
        list.add(RecipeListItem("x", "test3", false, "1"))
        list.add(RecipeListItem("x", "test4", false, "1"))
        list.add(RecipeListItem("x", "test5", false, "1"))
        list.add(RecipeListItem("x", "test6", false, "1"))
        val adapter = RecipeListAdapter(list)
        cardView_.adapter = adapter
        cardView_.layoutManager = gridLayoutManager
        gridLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (isSingleViewMode) 2 else 1
            }
        }
        passItemHeightToAdapter()
    }

    private fun passItemHeightToAdapter() {
        val dm = DisplayMetrics()
        window.windowManager.defaultDisplay.getMetrics(dm)
        val height = (dm.widthPixels * (if(isSingleViewMode) 1.2f else 0.6f)).toInt()
        (cardView_.adapter as RecipeListAdapter).itemHeight = height
        cardView_.adapter?.notifyDataSetChanged()
    }

    override fun onFilterModified(alcohol: RecipeListFilterDialog.Companion.ALCOHOL, ingrdCount: Int) {
        this.alcoholMode = alcohol
        this.ingrdCount = ingrdCount
        //Todo : Not implemented. 필터 적용 후 새로 고치는 로직 필요
    }

}
