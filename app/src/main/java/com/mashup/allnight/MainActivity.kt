package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.navigation.NavigationView
import com.mashup.allnight.adapter.MainListAdapter
import com.mashup.allnight.dataclass.MainListItem
import com.mashup.allnight.viewholder.MainListViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.main_nav_header.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mainAdapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMain()
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
        val arrList = ArrayList<MainListItem>()
        arrList.add(MainListItem(1,"vOeR6WsBTwvkY-8GnSmy", "no", "OH MY GOD1"))
        arrList.add(MainListItem(0,"7OeR6WsBTwvkY-8GnSnZ", "no", "OH MY GOD2"))
        arrList.add(MainListItem(0,"", "no", "OH MY GOD3"))
        arrList.add(MainListItem(0,"", "no", "OH MY GOD4"))
        arrList.add(MainListItem(0,"", "no", "OH MY GOD5"))

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
            startActivity(intent)
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.mnuScrap ->{
                val intent = Intent(this, RecipeListActivity::class.java)
                intent.putExtra(RecipeListActivity.RECIPE_LIST_TYPE_KEY, RecipeListActivity.RECIPE_LIST_TYPE_SCRAP)
                startActivity(intent)
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
