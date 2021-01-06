package com.steadytech.cekresi

import android.app.Application
import com.google.android.gms.ads.MobileAds
import io.realm.Realm

/**
Created By Faisal | Steady Tech.
------------ 29-12-2020 ------------
-------- Cek Resi --------
 **/

class ResiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        MobileAds.initialize(this)
    }
}