package com.steadytech.cekresi.helper

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.steadytech.cekresi.R
import com.steadytech.cekresi.constant.Constant

class GeneralHelper {
    companion object{
        @SuppressLint("UseCompatLoadingForDrawables")
        fun setCourierImage(imageView: ImageView, courierCode: String, activity: AppCompatActivity) {

            when (courierCode) {
                Constant.COURIER_KEY.JNE -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_jne))
                }
                Constant.COURIER_KEY.JNT -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_jnt))
                }
                Constant.COURIER_KEY.POS -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_pos_indonesia))
                }
                Constant.COURIER_KEY.SICEPAT -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_sicepat))
                }
                Constant.COURIER_KEY.TIKI -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_tiki))
                }
                Constant.COURIER_KEY.ANTER_AJA -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_anteraja))
                }
                Constant.COURIER_KEY.WAHANA -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_wahana))
                }
                Constant.COURIER_KEY.NINJA -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_ninja))
                }
                Constant.COURIER_KEY.LION -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_lion))
                }
                Constant.COURIER_KEY.PCP -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_pcp))
                }
                Constant.COURIER_KEY.JET -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_jet))
                }
                Constant.COURIER_KEY.REX -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_rex))
                }
                Constant.COURIER_KEY.FIRST -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_first_logistics))
                }
                Constant.COURIER_KEY.ID -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_id_express))
                }
                Constant.COURIER_KEY.SHOPEE -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_shopee_express))
                }
                Constant.COURIER_KEY.KGX -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_kgx))
                }
                Constant.COURIER_KEY.SAP -> {
                    imageView.setImageDrawable(activity.resources.getDrawable(R.drawable.logo_sap))
                }
            }
        }
    }
}