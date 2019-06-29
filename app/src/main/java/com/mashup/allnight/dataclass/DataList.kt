package com.mashup.allnight.dataclass

import java.io.Serializable

class DataList(name:String, checked:Boolean):Serializable{
    var name : String = name
    var checked : Boolean = false

}