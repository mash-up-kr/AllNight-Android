package com.mashup.allnight

import com.mashup.allnight.dataclass.DataList

interface ISearchResultItemCheckedListener {
    fun onResultItemChecked(data: DataList)
}