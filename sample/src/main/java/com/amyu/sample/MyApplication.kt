package com.amyu.sample

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen


class MyApplication :Application(){
    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
    }
}