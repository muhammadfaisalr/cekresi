package com.steadytech.cekresi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.minibugdev.sheetselection.SheetSelection
import com.minibugdev.sheetselection.SheetSelectionItem
import com.steadytech.cekresi.R
import com.steadytech.cekresi.adapter.AwbHistoryAdapter
import com.steadytech.cekresi.bottomsheet.CheckAwbBottomSheetFragment
import com.steadytech.cekresi.constant.Constant
import com.steadytech.cekresi.helper.FontsHelper
import com.steadytech.cekresi.model.awb.AWB

class CheckAwbFragment : Fragment(), View.OnClickListener {

    private lateinit var inputCourier : TextInputEditText
    private lateinit var inputAWB : TextInputEditText

    private lateinit var cardTracking : CardView
    private lateinit var buttonTracking : Button

    lateinit var courierCode : String;

    lateinit var awbAdapter: AwbHistoryAdapter

    lateinit var layoutManager : LinearLayoutManager

    lateinit var  tracks : ArrayList<AWB>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_check_awb, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.init(view)
    }

    private fun init(view: View) {

        this.inputCourier = view.findViewById(R.id.inputCourier)
        this.inputCourier.typeface =  FontsHelper.JOST.regular(this.activity!!)

        this.inputAWB = view.findViewById(R.id.inputAWB)
        this.inputAWB.typeface = FontsHelper.JOST.regular(this.activity!!)

        this.cardTracking = view.findViewById(R.id.cardTracking)
        this.buttonTracking = view.findViewById(R.id.buttonTracking)

        this.tracks = ArrayList()

        this.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)

        this.inputCourier.setOnClickListener(this)
        this.buttonTracking.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v!!
        if (v == this.inputCourier){
            val selectionList =
                listOf(
                    SheetSelectionItem("jne", "JNE", null),
                    SheetSelectionItem("jnt", "JNT", null),
                    SheetSelectionItem("pos", "POS Indonesia", null),
                    SheetSelectionItem("sicepat", "SiCepat", null),
                    SheetSelectionItem("tiki", "Tiki", null),
                    SheetSelectionItem("anteraja", "AnterAja", null),
                    SheetSelectionItem("wahana", "Wahana Express", null),
                    SheetSelectionItem("lion", "Lion Parcel", null),
                    SheetSelectionItem("pcp", "PCP Express", null),
                    SheetSelectionItem("jet", "JET Express", null),
                    SheetSelectionItem("wahana", "Wahana Express", null),
                    SheetSelectionItem("first", "First Logistics", null),
                    SheetSelectionItem("ide", "ID Express", null),
                    SheetSelectionItem("spx", "Shopee Express", null)
                )
            SheetSelection.Builder(this.activity!!)
                .theme(R.style.CustomSheetSelection)
                .title(this.resources.getString(R.string.select_courier))
                .items(selectionList)
                .showDraggedIndicator(true)
                .searchEnabled(true)
                .searchNotFoundText("Ekspedisi Tidak Tersedia!")
                .onItemClickListener { item, position ->
                    this.courierCode = item.key
                    this.inputCourier.setText(item.value)
                }
                .show()
        }else if (v == this.buttonTracking){
            if (this.inputAWB.text!!.isNotEmpty()){
                this.tracking()
            }else{
                this.inputAWB.error = this.getString(R.string.awb_number_empty)
            }
        }
    }

    private fun tracking() {
        val checkAwbBottomSheetFragment = CheckAwbBottomSheetFragment()
        val bundle = Bundle()

        bundle.putString(Constant.KEY.COURIER_CODE, this.courierCode)
        bundle.putString(Constant.KEY.AWB_NUMBER, this.inputAWB.text.toString())

        checkAwbBottomSheetFragment.arguments = bundle
        checkAwbBottomSheetFragment.show(this.activity!!.supportFragmentManager, "oke")
    }

}