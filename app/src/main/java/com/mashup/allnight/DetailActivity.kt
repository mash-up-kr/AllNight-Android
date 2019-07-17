package com.mashup.allnight

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.allnight.adapter.DrinkDetailAdapter
import com.mashup.allnight.dataclass.DrinkNeedSpecific
import com.mashup.allnight.dataclass.RecipeListItem
import com.mashup.allnight.retrofit.RetrofitManager
import com.mashup.allnight.retrofit.retrofitmodel.RetrofitCocktailDetailResponse
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity() {
    private var cocktailId: String? = ""
    private var isScrapped = -1 // -1: Unknown, 0: Not scrapped, 1: Scrapped

    private var menu: Menu? = null
    private var recipeListItemForScrap: RecipeListItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        cocktailId = intent.getStringExtra(COCKTAIL_ID_KEY)
        initDetail()
        requestDetailData()
    }

    private fun initDetail() {
        setSupportActionBar(detailToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            it.setDisplayShowTitleEnabled(false)
        }

        need_recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun requestDetailData() {
        if (cocktailId.isNullOrEmpty()) {
            Log.v(DetailActivity::class.java.simpleName, "Fail to request cocktail detail: Null or Empty CocktailId")
            showErrorToast("Fail to request: No cocktail ID")
            return
        }

        val req = RetrofitManager.createApi().getCocktailDetailResult(cocktailId!!)
        req.enqueue(object: Callback<RetrofitCocktailDetailResponse> {
            override fun onFailure(call: Call<RetrofitCocktailDetailResponse>, t: Throwable) {
                Log.v(DetailActivity::class.java.simpleName, "Fail to get cocktail detail: ${t.localizedMessage} ($cocktailId)")
                showErrorToast("Fail to request: No cocktail ID")
            }

            override fun onResponse(
                call: Call<RetrofitCocktailDetailResponse>,
                response: Response<RetrofitCocktailDetailResponse>
            ) {
                if (!response.isSuccessful) {
                    Log.v(DetailActivity::class.java.simpleName, "Fail to get cocktail detail: ${response.code()}")
                    showErrorToast("Fail to request: No cocktail ID")
                    return
                }

                response.body()?.let { setCocktailRecipeData(it) }
            }
        })
    }

    private fun setCocktailRecipeData(dataRes: RetrofitCocktailDetailResponse) {
        val isKorean = Locale.getDefault().language.startsWith("ko")
        ivDetailTitleImage.imgUrl = dataRes.thumbnailUrl
        tvDetailTitle.text = /*if (isKorean) dataRes.drinkName else*/ dataRes.drinkNameEng
        tvAlcoholic.text = dataRes.alcoHolic
        intro_passage.text = "" // 엥 이거 서버리스폰스에 없음 뭐지
        glass_passage.text = dataRes.glass
        need_recyclerView.adapter = DrinkDetailAdapter(createIngrdList(dataRes, isKorean))
        instruction_passage.text = dataRes.instruction

        // set scrapped info
        val isScrapped = ScrapManager.isContained(cocktailId!!)
        recipeListItemForScrap = RecipeListItem(
            dataRes.thumbnailUrl,
            dataRes.drinkNameEng,
            isScrapped,
            cocktailId!!,
            dataRes.alcoHolic)
        menu?.getItem(0)?.setIcon(
            if (isScrapped) R.drawable.ic_active_
            else R.drawable.ic_normal_
        )
        this.isScrapped = if (isScrapped) 1 else 0
    }

    private fun createIngrdList(dataRes: RetrofitCocktailDetailResponse, isKorean: Boolean)
            : ArrayList<DrinkNeedSpecific> {
        val list = ArrayList<DrinkNeedSpecific>()
        for (i in 0 until dataRes.ingredients.size) {
            list.add(
                DrinkNeedSpecific(
                    if (isKorean) dataRes.ingredients[i]
                    else dataRes.ingredientsEng[i],
                dataRes.measures[i].toInt()))
        }

        return list
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {  // ToolBar 에서 맨 좌측 버튼
                finish()
                true
            }
            R.id.menu_detail_scrap -> {
                when(isScrapped) {
                    0 -> {
                        isScrapped = 1
                        recipeListItemForScrap?.let { ScrapManager.addRecipeToScrapList(it) }
                        menu?.getItem(0)?.setIcon(R.drawable.ic_active_)
                    }
                    1 -> {
                        isScrapped = 0
                        recipeListItemForScrap?.let { ScrapManager.removeRecipeFromScrapList(it) }
                        menu?.getItem(0)?.setIcon(R.drawable.ic_normal_)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showErrorToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val COCKTAIL_ID_KEY = "cocktailId"
    }
}
