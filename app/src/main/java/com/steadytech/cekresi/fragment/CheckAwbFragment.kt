package com.steadytech.cekresi.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.textfield.TextInputEditText
import com.minibugdev.sheetselection.SheetSelection
import com.minibugdev.sheetselection.SheetSelectionItem
import com.steadytech.cekresi.R
import com.steadytech.cekresi.adapter.AwbTrackingAdapter
import com.steadytech.cekresi.bottomsheet.CheckAwbBottomSheetFragment
import com.steadytech.cekresi.constant.Constant
import com.steadytech.cekresi.helper.FontsHelper
import com.steadytech.cekresi.model.awb.AWB
import com.steadytech.cekresi.model.realm.AWBLocal
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class CheckAwbFragment : Fragment(), View.OnClickListener, Realm.Transaction {

    private lateinit var inputCourier : TextInputEditText
    private lateinit var inputAWB : TextInputEditText

    private lateinit var textBuildWithLove : TextView
    private lateinit var textMightYouLike : TextView

    private lateinit var buttonTracking : Button

    private lateinit var adView : AdView

    lateinit var courierCode : String
    lateinit var courierName : String

    lateinit var awbAdapter: AwbTrackingAdapter

    lateinit var layoutManager : LinearLayoutManager

    lateinit var  tracks : ArrayList<AWB>

    lateinit var realm : Realm

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_check_awb, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.init(view)
        this.setAds();
    }

    private fun setAds() {
        val adRequest = AdRequest.Builder().build()
        this.adView.loadAd(adRequest)

        this.adView.adListener = object : AdListener() {
            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                this@CheckAwbFragment.textMightYouLike.visibility = View.GONE
                Log.d("FailLoadAd", "Code Error : $p0")
            }
        }
    }

    private fun init(view: View) {
//        this.adView = view.findViewById(R.id.adView)

        this.inputCourier = view.findViewById(R.id.inputCourier)
        this.inputCourier.typeface =  FontsHelper.JOST.regular(this.activity!!)

//        this.textBuildWithLove = view.findViewById(R.id.textBuildWithLove)
//        this.textBuildWithLove.typeface = FontsHelper.JOST.medium(this.activity!!)

//        this.textMightYouLike = view.findViewById(R.id.textMightYouLike)
//        this.textMightYouLike.typeface = FontsHelper.JOST.medium(this.activity!!)

        this.inputAWB = view.findViewById(R.id.inputAWB)
        this.inputAWB.typeface = FontsHelper.JOST.regular(this.activity!!)

        this.buttonTracking = view.findViewById(R.id.buttonTracking)

        this.tracks = ArrayList()

        this.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)

        this.realm = Realm.getDefaultInstance()

        this.inputCourier.setOnClickListener(this)
        this.buttonTracking.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v!!
        if (v == this.inputCourier){
            val selectionList =
                listOf(
                    SheetSelectionItem(Constant.COURIER_KEY.JNE, "JNE", null),
                    SheetSelectionItem(Constant.COURIER_KEY.JNT, "J&T Express", null),
                    SheetSelectionItem(Constant.COURIER_KEY.POS, "POS Indonesia", null),
                    SheetSelectionItem(Constant.COURIER_KEY.SICEPAT, "SiCepat Express", null),
                    SheetSelectionItem(Constant.COURIER_KEY.TIKI, "Tiki", null),
                    SheetSelectionItem(Constant.COURIER_KEY.ANTER_AJA, "AnterAja", null),
                    SheetSelectionItem(Constant.COURIER_KEY.WAHANA, "Wahana Express", null),
                    SheetSelectionItem(Constant.COURIER_KEY.NINJA, "Ninja Express", null),
                    SheetSelectionItem(Constant.COURIER_KEY.LION, "Lion Parcel", null),
                    SheetSelectionItem(Constant.COURIER_KEY.PCP, "PCP Express", null),
                    SheetSelectionItem(Constant.COURIER_KEY.JET, "JET Express", null),
                    SheetSelectionItem(Constant.COURIER_KEY.REX, "REX Express", null),
                    SheetSelectionItem(Constant.COURIER_KEY.FIRST, "First Logistics", null),
                    SheetSelectionItem(Constant.COURIER_KEY.ID, "ID Express", null),
                    SheetSelectionItem(Constant.COURIER_KEY.SHOPEE, "Shopee Express", null),
                    SheetSelectionItem(Constant.COURIER_KEY.KGX, "KGXpress", null),
                    SheetSelectionItem(Constant.COURIER_KEY.SAP, "SAP Express", null)
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
                    this.courierName = item.value
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

        this.realm.executeTransaction(this)

        bundle.putString(Constant.KEY.COURIER_CODE, this.courierCode)
        bundle.putString(Constant.KEY.AWB_NUMBER, this.inputAWB.text.toString())

        checkAwbBottomSheetFragment.arguments = bundle
        checkAwbBottomSheetFragment.show(this.activity!!.supportFragmentManager, "CheckAwbFragment")

    }

    override fun execute(realm: Realm) {
        var awbLocal = realm.where<AWBLocal>().equalTo("awbNumber", this.inputAWB.text.toString()).findFirst()

            if (awbLocal == null){
                awbLocal = realm.createObject(this.inputAWB.text.toString())

                awbLocal.courierCode = this.courierCode
                awbLocal.courierName = this.courierName

                realm.copyToRealmOrUpdate(awbLocal)
        }
    }
}