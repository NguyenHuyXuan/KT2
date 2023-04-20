package com.stimednp.crudrealtimedb.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle

object Const {
    val PATH_COLLECTION = "sanpham"
    val PATH_AGE = "GiaSP"
    val PATH_NAME = "TenSP"

    fun setTimeStamp(): Long {
        val time = (-1 * System.currentTimeMillis())
        return time
    }
}

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}