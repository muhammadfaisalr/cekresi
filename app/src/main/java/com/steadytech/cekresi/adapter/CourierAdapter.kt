package com.steadytech.cekresi.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.minibugdev.sheetselection.SheetSelectionItem
import com.steadytech.cekresi.R
import com.steadytech.cekresi.activity.DetailTrackingActivity
import com.steadytech.cekresi.constant.Constant
import com.steadytech.cekresi.helper.FontsHelper
import com.steadytech.cekresi.helper.GeneralHelper

class CourierAdapter(
    private val context: Context,
    private val couriers: List<SheetSelectionItem>,
    private val awbNumber: String
) : RecyclerView.Adapter<CourierAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var textCourierName: TextView
        private lateinit var imageCourier: ImageView

        fun bind(context: Context, courier: SheetSelectionItem) {
            this.textCourierName = itemView.findViewById(R.id.textCourierName)
            this.imageCourier = itemView.findViewById(R.id.imageCourier)

            this.textCourierName.text = courier.value

            FontsHelper.JOST.medium(context as Activity, this.textCourierName)

            GeneralHelper.setCourierImage(this.imageCourier, courier.key, context as AppCompatActivity)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourierAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_courier, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CourierAdapter.ViewHolder, position: Int) {
        val courier = couriers[position]

        holder.bind(context, courier)
        holder.itemView.setOnClickListener {
            context.startActivity(
                Intent(context, DetailTrackingActivity::class.java)
                    .putExtra(Constant.KEY.AWB_NUMBER, awbNumber)
                    .putExtra(Constant.KEY.COURIER_CODE, courier.key)
                    .putExtra(Constant.KEY.COURIER_NAME, courier.value)
            )
        }
    }

    override fun getItemCount(): Int {
        return couriers.size
    }
}