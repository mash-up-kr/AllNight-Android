package com.mashup.allnight

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.navigation.NavigationView
import com.mashup.allnight.adapter.MainListAdapter
import com.mashup.allnight.dataclass.MainListItem
import com.mashup.allnight.dataclass.RecipeListItem
import com.mashup.allnight.retrofit.RetrofitManager
import com.mashup.allnight.retrofit.retrofitmodel.RetrofitCocktailListResponse
import com.mashup.allnight.viewholder.MainListViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.main_nav_header.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.text.util.Linkify
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.widget.TextView





class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mainAdapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMain()
        requestMainList()
    }

    private fun forNewRegistered(){
        //신규유저 Login2Activity로 이동
        val intent = Intent(this, Login2Activity::class.java)
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) // NO_HISTORY 안함
        startActivity(intent)
    }
    private fun initMain() {

        val pref = this.getSharedPreferences("prefs",0)

        if(pref.getBoolean("leadOff", true)){
            forNewRegistered()
        }

        // add temporary items
        val arrList = arrayListOf<MainListItem>()

        // initialize recyclerView
        mainAdapter = MainListAdapter(arrList)
        rvMain.adapter = mainAdapter
        val gridLayoutMgr = GridLayoutManager(this, 2)
        gridLayoutMgr.spanSizeLookup = object:GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                when(mainAdapter.getItemViewType(position)) {
                    MainListViewHolder.TYPE_RECOMMEND -> return 2
                    MainListViewHolder.TYPE_TODAY -> return 1
                    else -> return 2
                }
            }
        }

        rvMain.layoutManager = gridLayoutMgr

        // set listener of Views which inside of Drawer navigationView
        mainNavigationView.setNavigationItemSelectedListener(this)
        val navHeaderView = mainNavigationView.getHeaderView(0)
        ivOpenDrawer.setOnClickListener { drawerLayout.openDrawer(GravityCompat.END) }
        navHeaderView.ivCloseDrawer.setOnClickListener { drawerLayout.closeDrawer(GravityCompat.END) }

        // set fab listener
        fabSearch.setOnClickListener {
            val intent = Intent(this, DrinkKindActivity::class.java)
            intent.putExtra("bucket", false)
            startActivity(intent)
        }
    }

    private fun requestMainList() {
        val req = RetrofitManager.createApi().getCockTailMainList()
        req.enqueue(object: Callback<ArrayList<RetrofitCocktailListResponse>> {
            override fun onResponse(
                call: Call<ArrayList<RetrofitCocktailListResponse>>,
                response: Response<ArrayList<RetrofitCocktailListResponse>>
            ) {
                if (!response.isSuccessful) {
                    Log.v(RecipeListActivity::class.java.simpleName, "Fail to get response: ${response.code()}")
                    Toast.makeText(baseContext, "Fail to get response: ${response.code()}", Toast.LENGTH_LONG).show()
                    return
                }

                response.body()?.let { setCocktailListData(it) }
            }

            override fun onFailure(call: Call<ArrayList<RetrofitCocktailListResponse>>, t: Throwable) {
                Log.v(RecipeListActivity::class.java.simpleName, "Fail to get cocktail List: ${t.localizedMessage}")
                Toast.makeText(baseContext, "Fail to request: No cocktail ID", Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun setCocktailListData(dataArr: ArrayList<RetrofitCocktailListResponse>) {
        //val isKorean = Locale.getDefault().language.startsWith("ko")
        val list = ArrayList<MainListItem>()

        // shuffle dataArr to show randomly
        dataArr.shuffle()

        val scrappedList = App.prefs.getScrappedRecipeListFromPref()
        for(i in 0 until dataArr.size) {//} dataRes: RetrofitCocktailListResponse in dataArr) {
            // is scrapped?
            var isScrapped = false
            for (item: RecipeListItem in scrappedList) {
                if (item.id == dataArr[i].id) {
                    isScrapped = true
                    break
                }
            }
            val viewType = if (i == 0) 1 else 0
            list.add(MainListItem(
                viewType,
                dataArr[i].id,
                dataArr[i].thumbnailUrl,
                dataArr[i].drinkNameEng,
                dataArr[i].alcoHolic,
                isScrapped))
        }

        (rvMain.adapter as MainListAdapter).setData(list)
    }


    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.mnuScrap ->{
                val intent = Intent(this, RecipeListActivity::class.java)
                intent.putExtra(RecipeListActivity.RECIPE_LIST_TYPE_KEY, RecipeListActivity.RECIPE_LIST_TYPE_SCRAP)
                startActivity(intent)
            }
            R.id.mnuSetting -> {
                val intent = Intent(this, SettingsActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }
            R.id.mnuComplain ->{
                val gotoPlay = SpannableString("http://play.google.com/store/apps/details?id=com.mashup.allnight")
                Linkify.addLinks(gotoPlay, Linkify.ALL)
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.alert_dialog_title).setMessage(gotoPlay)
                builder.setPositiveButton(R.string.alert_dialog_ok) {dialog, id ->
                }
                val alertDialog = builder.create()
                alertDialog.show()

                (alertDialog.findViewById(android.R.id.message) as TextView).movementMethod = LinkMovementMethod.getInstance()
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }
}
