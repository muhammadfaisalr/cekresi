package com.steadytech.cekresi.helper

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

/**
Created By Faisal | Steady Tech.
------------ 14-11-2020 ------------
-------- Cek Resi --------
 **/

class FontsHelper {

    class JOST {
        companion object {
            fun regular(activity: Activity, vararg views: Any?){
                for (view in views){
                    when (view) {
                        is TextView -> {
                            view.typeface = Typeface.createFromAsset(activity.assets, "font/jost_regular.ttf")
                        }
                        is TextInputEditText -> {
                            view.typeface = Typeface.createFromAsset(activity.assets, "font/jost_regular.ttf")
                        }
                        is MaterialButton -> {
                            view.typeface = Typeface.createFromAsset(activity.assets, "font/jost_regular.ttf")
                        }
                    }
                }
            }
            fun lightItalic(activity: Activity, vararg views: Any?){
                for (view in views){
                    when (view) {
                        is TextView -> {
                            view.typeface = Typeface.createFromAsset(activity.assets, "font/jost_light_italic.ttf")
                        }
                        is TextInputEditText -> {
                            view.typeface = Typeface.createFromAsset(activity.assets, "font/jost_light_italic.ttf")
                        }
                        is MaterialButton -> {
                            view.typeface = Typeface.createFromAsset(activity.assets, "font/jost_light_italic.ttf")
                        }
                    }
                }
            }

            fun medium(activity: Activity, vararg views: Any?){
                for (view in views){
                    when (view) {
                        is TextView -> {
                            view.typeface = Typeface.createFromAsset(activity.assets, "font/jost_medium.ttf")
                        }
                        is TextInputEditText -> {
                            view.typeface = Typeface.createFromAsset(activity.assets, "font/jost_medium.ttf")
                        }
                        is MaterialButton -> {
                            view.typeface = Typeface.createFromAsset(activity.assets, "font/jost_medium.ttf")
                        }
                    }
                }
            }

            fun regular(activity: Activity) : Typeface{
                return Typeface.createFromAsset(activity.assets, "font/jost_regular.ttf")
            }
            fun medium (activity: Activity) : Typeface {
                return Typeface.createFromAsset(activity.assets, "font/jost_medium.ttf")
            }
        }
    }
}