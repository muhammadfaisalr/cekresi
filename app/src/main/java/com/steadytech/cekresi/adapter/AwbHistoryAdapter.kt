package com.steadytech.cekresi.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.steadytech.cekresi.R
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

class AwbHistoryAdapter(private val airwaybills : RealmResults<AWBLocal>, private val activity : AppCompatActivity, private val canSave : Boolean) : RecyclerView.Adapter<AwbHistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AwbHistoryAdapter.ViewHolder {
        return  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false))
    }

    override fun getItemCount(): Int {
        return this.airwaybills.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: AwbHistoryAdapter.ViewHolder, position: Int) {
        val awbLocal = this.airwaybills[position]

        holder.textAwbNumber.text = awbLocal!!.awbNumber

        GeneralHelper.setCourierImage(holder.imageLogo, awbLocal.courierCode, activity)

        holder.textAwbNumber.typeface = FontsHelper.JOST.medium(this.activity)

        holder.itemView.setOnClickListener {
            val checkAwbBottomSheetFragment = CheckAwbBottomSheetFragment()
            val bundle = Bundle()

            bundle.putString(Constant.KEY.COURIER_CODE, awbLocal.courierCode)
            bundle.putString(Constant.KEY.AWB_NUMBER, awbLocal.awbNumber)
            bundle.putBoolean(Constant.KEY.COMMON_ARGUMENT, canSave)

            checkAwbBottomSheetFragment.arguments = bundle
            checkAwbBottomSheetFragment.show(this.activity.supportFragmentManager, "CheckAwbFragment")
        }
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        var textAwbNumber : TextView = containerView.findViewById(R.id.textAwbNumber)
        var imageLogo : ImageView = containerView.findViewById(R.id.imageLogo)
    }
}