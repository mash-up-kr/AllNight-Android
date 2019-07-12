package com.mashup.allnight.customview

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.Checkable
import androidx.core.view.children
import com.mashup.allnight.IRecipeListFilterModifiedListener
import com.mashup.allnight.R
import kotlinx.android.synthetic.main.dialog_recipe_list_filter.*

class RecipeListFilterDialog(context: Context, themeResId: Int, val filterModifiedListener: IRecipeListFilterModifiedListener)
    : Dialog(context, themeResId) {

    var selectedALCOHOL = ALCOHOL.ALL
    var selectedIngrdCnt = 3

    init {
        setContentView(R.layout.dialog_recipe_list_filter)

        // checkbox listener
        setAllChkBoxOnClkListener(layout_recipe_filter)

        // apply listener
        btnFilterApply.setOnClickListener { filterModifiedListener.onFilterModified(selectedALCOHOL, selectedIngrdCnt); dismiss() }

        window?.let {wdw ->
            wdw.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            val windowLayoutParam = window?.attributes
            windowLayoutParam?.let {wlp ->
                wlp.gravity = Gravity.BOTTOM
                wdw.attributes = wlp
            }
        }
    }

    private fun setAllChkBoxOnClkListener(view: View) {
        if (view is ViewGroup) {
            for (child in view.children)
                setAllChkBoxOnClkListener(child)
        } else if (view is Checkable) {
            view.setOnClickListener { inGroupFilterChecked(it) }
        }
    }

    private fun inGroupFilterChecked(view: View) {
        val parent: ViewGroup = view.parent as ViewGroup

        for (childView: View in parent.children) {
            if(childView !is Checkable) continue
            childView.isChecked = childView == view
        }

        // set values
        if (view is Checkable) {
            when (view) {
                chkFilterAll -> selectedALCOHOL = Companion.ALCOHOL.ALL
                chkFilterAlcohol -> selectedALCOHOL = Companion.ALCOHOL.ALCOHOL
                chkFilterNonAlcohol -> selectedALCOHOL = Companion.ALCOHOL.NON_ALCOHOL

                chkFilterIngrd3 -> selectedIngrdCnt = 3
                chkFilterIngrd4 -> selectedIngrdCnt = 4
                chkFilterIngrd5 -> selectedIngrdCnt = 5
            }
        }
    }

    fun setCheckState(state: ALCOHOL, count: Int) {
        selectedALCOHOL = state
        selectedIngrdCnt = count
        setAlcoholCheck(state)
        setIngrdCountCheck(count)
    }

    private fun setAlcoholCheck(state: ALCOHOL) {
        when(state) {
            Companion.ALCOHOL.ALL -> {chkFilterAll.isChecked = true; selectedALCOHOL = Companion.ALCOHOL.ALL }
            Companion.ALCOHOL.ALCOHOL -> {chkFilterAlcohol.isChecked = true; selectedALCOHOL = Companion.ALCOHOL.ALCOHOL}
            Companion.ALCOHOL.NON_ALCOHOL -> {chkFilterNonAlcohol.isChecked = true; selectedALCOHOL = Companion.ALCOHOL.NON_ALCOHOL }
        }
    }

    private fun setIngrdCountCheck(count: Int) {
        when {
            count <= 3 -> chkFilterIngrd3.isChecked = true
            count == 4 -> chkFilterIngrd4.isChecked = true
            count >= 5 -> chkFilterIngrd5.isChecked = true
        }
        selectedIngrdCnt = count
    }

    companion object {
        enum class ALCOHOL {ALL, ALCOHOL, NON_ALCOHOL}
    }
}