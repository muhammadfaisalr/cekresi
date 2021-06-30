package com.steadytech.cekresi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minibugdev.sheetselection.SheetSelectionItem
import com.steadytech.cekresi.R
import com.steadytech.cekresi.adapter.CourierAdapter
import com.steadytech.cekresi.constant.Constant
import com.steadytech.cekresi.databinding.ActivityListCourierBinding
import com.steadytech.cekresi.helper.FontsHelper

class ListCourierActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var textSelectCourier : TextView
    private lateinit var textAwb : TextView
    private lateinit var imageBack : ImageView

    private lateinit var recyclerView : RecyclerView

    private lateinit var binding : ActivityListCourierBinding

    private lateinit var awbNum : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityListCourierBinding.inflate(this.layoutInflater)
        super.setContentView(this.binding.root)
        this.extract()
        this.init()
    }

    private fun extract() {
        this.awbNum = this.intent.getStringExtra(Constant.KEY.AWB_NUMBER)!!
    }

    private fun init() {
        this.textSelectCourier = this.binding.textSelectCourier
        this.textAwb = this.binding.textAwb
        this.imageBack = this.binding.imageBack
        this.recyclerView = this.binding.recyclerView

        this.textAwb.text = "No Resi : $awbNum"

        val selectionList =
            listOf(
                SheetSelectionItem("jne", "JNE", null),
                SheetSelectionItem("jnt", "JNT", null),
                SheetSelectionItem("pos", "POS Indonesia", null),
                SheetSelectionItem("sicepat", "SiCepat", null),
                SheetSelectionItem("tiki", "Tiki", null),
                SheetSelectionItem("anteraja", "AnterAja", null),
                SheetSelectionItem("wahana", "Wahana Express", null),
                SheetSelectionItem("ninja", "Ninja", null),
                SheetSelectionItem("lion", "Lion Parcel", null),
                SheetSelectionItem("pcp", "PCP Express", null),
                SheetSelectionItem("jet", "JET Express", null),
                SheetSelectionItem("rex", "REX Express", null),
                SheetSelectionItem("first", "First Logistics", null),
                SheetSelectionItem("ide", "ID Express", null),
                SheetSelectionItem("spx", "Shopee Express", null),
                SheetSelectionItem("kgx", "KGXpress", null),
                SheetSelectionItem("sap", "SAP Express", null)
            )

        this.recyclerView.layoutManager = GridLayoutManager(this, 3)
        this.recyclerView.adapter = CourierAdapter(this, selectionList, this.awbNum)

        FontsHelper.JOST.medium(this, this.textSelectCourier)
        FontsHelper.JOST.regular(this, this.textAwb)

        this.imageBack.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0 == this.imageBack){
            this.back()
        }
    }

    private fun back() {
        this.finish()
    }
}