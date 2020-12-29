package com.steadytech.cekresi.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.steadytech.cekresi.R
import com.steadytech.cekresi.adapter.AwbHistoryAdapter
import com.steadytech.cekresi.builder.RetrofitBuilder
import com.steadytech.cekresi.constant.Constant
import com.steadytech.cekresi.helper.FontsHelper
import com.steadytech.cekresi.model.awb.AWB
import com.steadytech.cekresi.service.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class CheckAwbBottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {

    lateinit var courierCode : String
    lateinit var awbNumber : String

    lateinit var textTitle : TextView
    lateinit var textSender : TextView
    lateinit var textOrigin : TextView
    lateinit var textReceiver : TextView
    lateinit var textDestination : TextView
    lateinit var textCourier : TextView
    lateinit var textStatus : TextView
    lateinit var textError : TextView

    lateinit var imageClose : ImageView
    lateinit var imageSave : ImageView

    lateinit var recyclerTrack : RecyclerView

    lateinit var linearContent : LinearLayout
    lateinit var linearEmpty : LinearLayout

    lateinit var lottieLoading : LottieAnimationView

    lateinit var tracks : ArrayList<AWB>

    lateinit var awbHistoryAdapter : AwbHistoryAdapter
    lateinit var layoutManager : LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_check_awb_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.init(view)

        this.tracking(view)

    }

    private fun init(view: View) {
        this.courierCode = this.arguments!!.getString(Constant.KEY.COURIER_CODE, null)
        this.awbNumber = this.arguments!!.getString(Constant.KEY.AWB_NUMBER, null)

        this.tracks = ArrayList()

        this.linearContent = view.findViewById(R.id.linearContent)
        this.linearEmpty = view.findViewById(R.id.linearEmpty)

        this.lottieLoading = view.findViewById(R.id.lottieLoading)

        this.textTitle = view.findViewById(R.id.textAwbNumber)
        this.textTitle.typeface = FontsHelper.JOST.medium(this.activity!!)
        this.textTitle.text = this.awbNumber

        this.textSender = view.findViewById(R.id.textSender)
        this.textSender.typeface = FontsHelper.JOST.medium(this.activity!!)

        this.textOrigin = view.findViewById(R.id.textOrigin)
        this.textOrigin.typeface = FontsHelper.JOST.medium(this.activity!!)

        this.textReceiver = view.findViewById(R.id.textReceiver)
        this.textReceiver.typeface = FontsHelper.JOST.medium(this.activity!!)

        this.textDestination = view.findViewById(R.id.textDestination)
        this.textDestination.typeface = FontsHelper.JOST.medium(this.activity!!)

        this.textCourier = view.findViewById(R.id.textCourier)
        this.textCourier.typeface = FontsHelper.JOST.medium(this.activity!!)

        this.textStatus = view.findViewById(R.id.textStatus)
        this.textStatus.typeface = FontsHelper.JOST.medium(this.activity!!)

        this.textError = view.findViewById(R.id.textError)
        this.textError.typeface = FontsHelper.JOST.medium(this.activity!!)

        this.recyclerTrack = view.findViewById(R.id.recyclerTrack)

        this.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)

        this.imageClose = view.findViewById(R.id.imageClose)
        this.imageSave = view.findViewById(R.id.imageSave)

        this.imageClose.setOnClickListener(this)
        this.imageSave.setOnClickListener(this)

        Log.d("CheckAWBStarted", "Search Package from AWB Num : " + this.awbNumber)

    }


    @SuppressLint("SetTextI18n")
    private fun tracking(view: View) {

        if (this.tracks.isNotEmpty()){
            this.tracks.clear()
        }

        val retrofit = RetrofitBuilder.checkAwb().create(RetrofitService::class.java)
        CompositeDisposable().add(
            retrofit.getAwbDetail("a4217141d198d98045b69a4006bdec633e5d5931e5b78eb87f4b7b0eadsasdhais-5", this.courierCode, this.awbNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d("Success Get Data", it.message )
                        this.tracks.add(it.data)


                        this.linearContent.visibility = View.VISIBLE
                        this.linearEmpty.visibility = View.GONE
                        this.imageSave.visibility = View.VISIBLE
                        this.lottieLoading.visibility = View.GONE

                        //Start Get Detail AWB
                        val awbDetail = it.data.detail
                        this.textSender.text = awbDetail.shipper
                        this.textOrigin.text = awbDetail.origin
                        this.textReceiver.text = awbDetail.receiver
                        this.textDestination.text = awbDetail.destination
                        //End Get Detail AWB

                        //Start Get Summary AWB
                        val awbSummary = it.data.summary
                        this.textCourier.text = awbSummary.courier + "[" + awbSummary.service + "]"
                        this.textStatus.text = awbSummary.status
                        //End Get Summary AWB

                        //Start Get History AWB
                        this.awbHistoryAdapter = AwbHistoryAdapter(it.data.history, this.activity!!)
                        this.recyclerTrack.adapter = this.awbHistoryAdapter
                        this.recyclerTrack.layoutManager = layoutManager
                        //End Get History AWB

                    },
                    {
                        println("StartError")
                        println(it.message)
                        this.linearContent.visibility = View.GONE
                        this.linearEmpty.visibility = View.VISIBLE
                        this.lottieLoading.visibility = View.GONE
                        this.imageSave.visibility = View.GONE
                        println("EndError")
                    }
                )
        )
    }

    override fun onClick(view: View?) {
        if (view!! == this.imageClose){
            this.dismiss()
        }
    }

}