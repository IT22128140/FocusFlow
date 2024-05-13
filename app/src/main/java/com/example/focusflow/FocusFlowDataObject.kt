package com.example.focusflow


object FocusFlowDataObject {
    var listdata = mutableListOf<CardInfo>()

    fun setData(title: String, desc: String, priority: String, dueTime:String, status:Boolean) {
        listdata.add(CardInfo(title, desc, priority, dueTime, status))
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

    fun updateData(pos: Int, title: String, desc: String, priority: String, dueTime: String)
    {
        listdata[pos].title=title
        listdata[pos].desc=desc
        listdata[pos].priority=priority
        listdata[pos].dueTime=dueTime
    }
    fun updateStatus(pos: Int, status: Boolean){
        listdata[pos].status=status
    }

}