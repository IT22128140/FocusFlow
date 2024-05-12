package com.example.focusflow


object FocusFlowDataObject {
    var listdata = mutableListOf<CardInfo>()

    fun setData(title: String, desc: String, priority: String) {
        listdata.add(CardInfo(title, desc, priority))
    }

    fun getAllData(): List<CardInfo> {
        return listdata
    }

    fun deleteAll(){
        listdata.clear()
    }

    fun getData(pos:Int): CardInfo {
        return listdata[pos]
    }

    fun deleteData(pos:Int){
        listdata.removeAt(pos)
    }

    fun updateData(pos: Int, title: String, desc: String, priority: String)
    {
        listdata[pos].title=title
        listdata[pos].desc=desc
        listdata[pos].priority=priority
    }

}