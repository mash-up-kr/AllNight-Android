package com.mashup.allnight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.allnight.adapter.DrinkDetailAdapter
import com.mashup.allnight.dataclass.DrinkNeedSpecific
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initDetail()


        need_recyclerView.adapter = DrinkDetailAdapter(init())
        need_recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initDetail() {
        setSupportActionBar(detailToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_detail_scrap -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun init(): ArrayList<DrinkNeedSpecific>{
        // 임시 작동확인용 데이터 삽입
        val temp_item = ArrayList<DrinkNeedSpecific>()
        temp_item.add(DrinkNeedSpecific("맥주", 10))
        temp_item.add(DrinkNeedSpecific("물", 1))
        temp_item.add(DrinkNeedSpecific("고량주", 1))

        return temp_item
    }
}
