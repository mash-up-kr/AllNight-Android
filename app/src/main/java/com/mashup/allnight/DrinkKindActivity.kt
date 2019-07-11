package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.allnight.adapter.SearchResultAdapter
import com.mashup.allnight.dataclass.DataList
import com.mashup.allnight.retrofit.RetrofitManager
import kotlinx.android.synthetic.main.activity_drink_kind.*
import kotlinx.android.synthetic.main.activity_login2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.R.string.cancel
import android.os.Handler
import kotlinx.android.synthetic.main.activity_drink_kind.view.*
import java.util.*


class DrinkKindActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_kind)

        val adapter = SearchResultAdapter(arrayListOf())
        search_item_recyclerview.adapter = adapter
        search_item_recyclerview.layoutManager = LinearLayoutManager(this)

        var checkedList: ArrayList<DataList> = arrayListOf()
        next_button.setOnClickListener{
            checkedList.clear()
            for(i in 0 until adapter.getItemCount()){
                if(adapter.getItem(i).checked){
                    checkedList.add(adapter.getItem(i))
                }
            }

            val nextIntent = Intent(this, BucketActivity::class.java)
            nextIntent.putExtra("checked", checkedList)
            startActivity(nextIntent)
        }


        btnSearch.setOnClickListener {
            val call = RetrofitManager.IRetrofitApi.getSearchIngredientListResult(editText.text.toString())
            call.enqueue(object:Callback<ArrayList<String>> {
                override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                }

                override fun onResponse(call: Call<ArrayList<String>>, response: Response<ArrayList<String>>) {
                    val itemList: MutableList<DataList> = arrayListOf()
                    response.body()?.let {
                        for(item in it) {
                            val item = DataList(item, false)
                            itemList.add(item)
                        }
                    }

                    (search_item_recyclerview.adapter as SearchResultAdapter).setItemList(itemList)
                }

            })
        }

        back_button.setOnClickListener{
            finish()
        }

        next_button.setOnClickListener{
            val intent = Intent(this, BucketActivity::class.java)
            startActivity(intent)
        }

    }
}
