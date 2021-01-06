package com.steadytech.cekresi.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.steadytech.cekresi.R
import com.steadytech.cekresi.helper.FontsHelper
import com.steadytech.cekresi.model.awb.AWBHistory
import kotlinx.android.extensions.LayoutContainer

class AwbTrackingAdapter(private val awb: ArrayList<AWBHistory>, private val activity: Activity) : RecyclerView.Adapter<AwbTrackingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AwbTrackingAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tracking_history, parent, false))
    }

    override fun getItemCount(): Int {
        return this.awb.size
    }

    override fun onBindViewHolder(holder: AwbTrackingAdapter.ViewHolder, position: Int) {
        val awbHistory = awb[position]

        holder.textDate.text = awbHistory.date
        holder.textDate.typeface = FontsHelper.JOST.medium(this.activity)
        holder.textDesc.text = awbHistory.desc
        holder.textDesc.typeface = FontsHelper.JOST.regular(this.activity)
    }


    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        var textDate : TextView = containerView.findViewById(R.id.textDate)
        var textDesc : TextView = containerView.findViewById(R.id.textDesc)
    }

}