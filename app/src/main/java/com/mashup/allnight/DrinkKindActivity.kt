package com.mashup.allnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.allnight.adapter.SearchResultAdapter
import com.mashup.allnight.dataclass.DataList
import com.mashup.allnight.retrofit.RetrofitManager
import kotlinx.android.synthetic.main.activity_drink_kind.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class DrinkKindActivity : ISearchResultItemCheckedListener, AppCompatActivity() {

    var checkedList: ArrayList<DataList> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_kind)

        next_button.isEnabled = false

        val adapter = SearchResultAdapter(arrayListOf(), this)
        search_item_recyclerview.adapter = adapter
        search_item_recyclerview.layoutManager = LinearLayoutManager(this)

        next_button.setOnClickListener{
            val nextIntent = Intent(this, BucketActivity::class.java)
            nextIntent.putExtra("checked", checkedList)
            startActivity(nextIntent)
        }

        btnSearch.setOnClickListener {
            val call = RetrofitManager.createApi().getSearchIngredientListResult(editText.text.toString().trim())
            call.enqueue(object:Callback<ArrayList<String>> {
                override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                }

                override fun onResponse(call: Call<ArrayList<String>>, response: Response<ArrayList<String>>) {
                    val itemList: MutableList<DataList> = arrayListOf()
                    response.body()?.let {
                        for(item in it) {
                            val item = DataList(item, false)
                            for(checkedItem in checkedList){
                                if(checkedItem.name == item.name)
                                    item.checked = true
                            }
                            itemList.add(item)
                        }
                    }

                    (search_item_recyclerview.adapter as SearchResultAdapter).setItemList(itemList)
                    if(checkedList.size > 0)
                        next_button.isEnabled = true
                    else
                        next_button.isEnabled = false
                }

            })
        }

        editText.setOnEditorActionListener{_, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                btnSearch.performClick()
                true
            }
            else{
                false
            }

        }

        back_button.setOnClickListener{
            finish()
        }
    }

    override fun onResultItemChecked(data: DataList){
        val adapter = search_item_recyclerview.adapter as SearchResultAdapter

        adapter?.let {
            for (i in 0 until it.getItemCount()) {
                if (it.getItem(i).checked) {
                    next_button.isEnabled = true
                    break
                }
                if (i + 1 == it.getItemCount()) {
                    next_button.isEnabled = false
                }
            }
        }
        if(data.checked){
            if(checkedList.size == 0){
                checkedList.add(data)
            }
            else {
                for (i in 0 until checkedList.size) {
                    if (checkedList[i].name == data.name)
                        break
                    if (i + 1 == checkedList.size)
                        checkedList.add(data)
                }
            }
        }
        else
            checkedList.removeAll { dataList -> data.name == dataList.name }
    }
}
