package com.steadytech.cekresi.helper

import android.app.Activity
import android.content.Context
import android.graphics.Typeface

/**
Created By Faisal | Steady Tech.
------------ 14-11-2020 ------------
-------- Cek Resi --------
 **/

class FontsHelper {

    class JOST {
        companion object {
            fun regular(activity: Activity) : Typeface{
                return Typeface.createFromAsset(activity.assets, "font/jost_regular.ttf")
            }
            fun medium (activity: Activity) : Typeface {
                return Typeface.createFromAsset(activity.assets, "font/jost_medium.ttf")
            }
        }
    }
}