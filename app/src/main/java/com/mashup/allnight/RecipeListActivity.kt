package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class RecipeListActivity : AppCompatActivity(), IRecipeListFilterModifiedListener, IScrappedRecipeListRequestListener {

    private var alcoholMode : RecipeListFilterDialog.Companion.ALCOHOL = RecipeListFilterDialog.Companion.ALCOHOL.ALCOHOL
    private var ingrdCount = 3
    private var ingrdSearchList = ArrayList<String>()

    private val loadCountOnce = 16

    private var isSingleViewMode = true
    private val gridLayoutManager = GridLayoutManager(this, 2)

    private var recipeListType: String = RECIPE_LIST_TYPE_SEARCH

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
            passItemViewModeToAdapter()
        }

        BtnFilter.setOnClickListener {
            val dialog = RecipeListFilterDialog(this, R.style.DialogFromBottom, this)
            dialog.setCheckState(alcoholMode, ingrdCount)
            dialog.show()
        }

        init()
        getRecipeListData()
    }

    private fun init() {
        intent.getStringExtra(RECIPE_LIST_TYPE_KEY)?.let {
            recipeListType = it
        }

        intent.getStringArrayListExtra("item")?.let {
            for(name: String in it)
                ingrdSearchList.add(name)
        }

        val list = ArrayList<RecipeListItem>()
        val adapter = RecipeListAdapter(list, this)
        cardView_.adapter = adapter
        cardView_.layoutManager = gridLayoutManager
        gridLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (isSingleViewMode) 2 else 1
            }
        }
        passItemViewModeToAdapter()
    }

    private fun getRecipeListData() {
        when (recipeListType) {
            RECIPE_LIST_TYPE_SEARCH -> requestRecipeListDataToServer()
            RECIPE_LIST_TYPE_SCRAP -> getRecipeListDataFromScrapped()
        }
    }

    private fun requestRecipeListDataToServer() {

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
        val scrappedList = App.prefs.getScrappedRecipeListFromPref()
        for(dataRes: RetrofitCocktailListResponse in dataArr) {
            // is scrapped?
            var isScrapped = false
            for (item: RecipeListItem in scrappedList) {
                if (item.id == dataRes.id) {
                    isScrapped = true
                    break
                }
            }

            list.add(RecipeListItem(
                dataRes.thumbnailUrl,
                if (isKorean) dataRes.drinkName else dataRes.drinkNameEng,
                isScrapped,
                dataRes.id,
                dataRes.alcoHolic))
        }

        (cardView_.adapter as RecipeListAdapter).setData(list)
    }

    private fun getRecipeListDataFromScrapped() {
        val scrappedList = App.prefs.getScrappedRecipeListFromPref()

        val iterator = scrappedList.iterator()
        while (iterator.hasNext()) {
            // filtering alcoholic
            val savedAlcoholMode = iterator.next().alcoholic
            when (alcoholMode) {
                RecipeListFilterDialog.Companion.ALCOHOL.ALCOHOL -> {
                    if (savedAlcoholMode.toLowerCase() != "alcoholic") iterator.remove()
                }
                RecipeListFilterDialog.Companion.ALCOHOL.NON_ALCOHOL -> {
                    if (savedAlcoholMode.toLowerCase() == "alcoholic") iterator.remove()
                }
                RecipeListFilterDialog.Companion.ALCOHOL.ALL -> {/*Keep list*/}
            }
        }

        // Todo: 재료 수 조건이 정확히 뭐지. 일치인지 아니면 이상인지 아니면 이하인지..

        (cardView_.adapter as RecipeListAdapter).setData(scrappedList)
    }

    private fun passItemViewModeToAdapter() {
        (cardView_.adapter as RecipeListAdapter).isSingleItemViewMode = isSingleViewMode
        cardView_.adapter?.notifyDataSetChanged()
    }

    override fun onFilterModified(alcohol: RecipeListFilterDialog.Companion.ALCOHOL, ingrdCount: Int) {
        this.alcoholMode = alcohol
        this.ingrdCount = ingrdCount
        getRecipeListData()
    }

    override fun addRecipeToScrapList(recipeListItem: RecipeListItem) {
        val scrappedList = App.prefs.getScrappedRecipeListFromPref()
        // check contains
        for(item: RecipeListItem in scrappedList) {
            if (item.id == recipeListItem.id) return
        }
        scrappedList.add(recipeListItem)
        App.prefs.setScrappedRecipeListFromPref(scrappedList)
    }

    override fun removeRecipeFromScrapList(recipeListItem: RecipeListItem) {
        val scrappedList = App.prefs.getScrappedRecipeListFromPref()
        // check contains
        val iterator = scrappedList.iterator()
        while (iterator.hasNext()) {
            val id = iterator.next().id
            if (recipeListItem.id == id) iterator.remove()
        }
        App.prefs.setScrappedRecipeListFromPref(scrappedList)
    }

    private fun showErrorToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val RECIPE_LIST_TYPE_KEY = "recipe_list_type"
        const val RECIPE_LIST_TYPE_SEARCH = "search"
        const val RECIPE_LIST_TYPE_SCRAP = "scrap"
    }

}
