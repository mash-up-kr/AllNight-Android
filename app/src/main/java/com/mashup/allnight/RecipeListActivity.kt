package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.mashup.allnight.adapter.RecipeListAdapter
import com.mashup.allnight.customview.RecipeListFilterDialog
import com.mashup.allnight.dataclass.RecipeListItem
import com.mashup.allnight.retrofit.RetrofitManager
import com.mashup.allnight.retrofit.retrofitmodel.RetrofitCocktailListResponse
import kotlinx.android.synthetic.main.mix_recipe_single.*
import kotlinx.android.synthetic.main.mix_recipe_single.BtnFilter
import kotlinx.android.synthetic.main.mix_recipe_single.BtnToHome_
import kotlinx.android.synthetic.main.mix_recipe_single.cardView_
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class RecipeListActivity : AppCompatActivity(),IRecipeListFilterModifiedListener {

    private var alcoholMode : RecipeListFilterDialog.Companion.ALCOHOL = RecipeListFilterDialog.Companion.ALCOHOL.ALCOHOL
    private var ingrdCount = 3
    private var ingrdSearchList = ArrayList<String>()

    private val loadCountOnce = 16

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
        requestRecipeListData()
    }

    private fun init() {
        intent.getStringArrayListExtra("item")?.let {
            for(name: String in it)
                ingrdSearchList.add(name)
        }

        val list = ArrayList<RecipeListItem>()
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

    private fun requestRecipeListData() {

        val reqMap = HashMap<String, String>()
        reqMap["offset"] = "0"
        reqMap["size"] = loadCountOnce.toString()
        reqMap["isAlcohol"] =
            if (alcoholMode == RecipeListFilterDialog.Companion.ALCOHOL.ALCOHOL) true.toString()
            else false.toString()
        reqMap["ingredientCount"] = ingrdCount.toString()

        val req = RetrofitManager.createApi().getSearchCocktailListResult(reqMap, ingrdSearchList.toTypedArray())
        req.enqueue(object: Callback<ArrayList<RetrofitCocktailListResponse>> {
            override fun onFailure(call: Call<ArrayList<RetrofitCocktailListResponse>>, t: Throwable) {
                Log.v(RecipeListActivity::class.java.simpleName, "Fail to get cocktail List: ${t.localizedMessage}")
                showErrorToast("Fail to request: No cocktail ID")
            }

            override fun onResponse(
                call: Call<ArrayList<RetrofitCocktailListResponse>>,
                response: Response<ArrayList<RetrofitCocktailListResponse>>
            ) {
                if (!response.isSuccessful) {
                    Log.v(RecipeListActivity::class.java.simpleName, "Fail to get response: ${response.code()}")
                    showErrorToast("Fail to get response: ${response.code()}")
                    return
                }

                response.body()?.let { setCocktailListData(it) }
            }

        })
    }

    private fun setCocktailListData(dataArr: ArrayList<RetrofitCocktailListResponse>) {
        val isKorean = Locale.getDefault().language.startsWith("ko")
        val list = ArrayList<RecipeListItem>()
        for(dataRes: RetrofitCocktailListResponse in dataArr) {
            list.add(RecipeListItem(
                dataRes.thumbnailUrl,
                if (isKorean) dataRes.drinkName else dataRes.drinkNameEng,
                false,
                dataRes.id,
                dataRes.alcoHolic))
        }

        (cardView_.adapter as RecipeListAdapter).setData(list)
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
        requestRecipeListData()
    }

    private fun showErrorToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}
