package com.steadytech.cekresi.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.steadytech.cekresi.R
import com.steadytech.cekresi.activity.DetailTrackingActivity
import com.steadytech.cekresi.bottomsheet.CheckAwbBottomSheetFragment
import com.steadytech.cekresi.constant.Constant
import com.steadytech.cekresi.helper.FontsHelper
import com.steadytech.cekresi.helper.GeneralHelper
import com.steadytech.cekresi.model.realm.AWBLocal
import io.realm.RealmResults
import kotlinx.android.extensions.LayoutContainer

/**
Created By Faisal | Steady Tech.
------------ 30-12-2020 ------------
-------- Cek Resi --------
 **/

class AwbHistoryAdapter(
    private val airwaybills: RealmResults<AWBLocal>,
    private val activity: AppCompatActivity,
    private val canSave: Boolean
) : RecyclerView.Adapter<AwbHistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AwbHistoryAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_awb, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return this.airwaybills.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: AwbHistoryAdapter.ViewHolder, position: Int) {
        val awbLocal = this.airwaybills[position]


        GeneralHelper.setCourierImage(holder.imageCourier, awbLocal!!.courierCode, activity)

        holder.textAwbNumber.text = awbLocal.awbNumber
        holder.textCourierName.text = awbLocal.courierName

        FontsHelper.JOST.regular(activity, holder.textCourierName)
        FontsHelper.JOST.medium(activity, holder.textAwbNumber)

        holder.itemView.setOnClickListener {
            activity.startActivity(
                Intent(activity, DetailTrackingActivity::class.java)
                    .putExtra(Constant.KEY.COURIER_CODE, awbLocal.courierCode)
                    .putExtra(Constant.KEY.COURIER_NAME, awbLocal.courierName)
                    .putExtra(Constant.KEY.AWB_NUMBER, awbLocal.awbNumber)
            )
        }
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        var textAwbNumber: TextView = containerView.findViewById(R.id.textAwb)
        var textCourierName: TextView = containerView.findViewById(R.id.textCourierName)
        var imageCourier: ImageView = containerView.findViewById(R.id.imageCourier)
    }
}