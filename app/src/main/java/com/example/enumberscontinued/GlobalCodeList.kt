package com.example.enumberscontinued

import android.content.Context

object GlobalCodeList : ECodeList() {
    var instance: ECodeList? = null
        private set

    fun init(context: Context?) {
        instance = ECodeList()
        instance!!.load(context!!)
    }
}