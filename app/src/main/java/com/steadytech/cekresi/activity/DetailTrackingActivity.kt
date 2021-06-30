package com.steadytech.cekresi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.steadytech.cekresi.adapter.AwbTrackingAdapter
import com.steadytech.cekresi.builder.RetrofitBuilder
import com.steadytech.cekresi.constant.Constant
import com.steadytech.cekresi.databinding.ActivityDetailTrackingBinding
import com.steadytech.cekresi.helper.FontsHelper
import com.steadytech.cekresi.model.awb.AWB
import com.steadytech.cekresi.model.realm.AWBLocal
import com.steadytech.cekresi.service.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_detail_tracking.*

class DetailTrackingActivity : AppCompatActivity(), View.OnClickListener, Realm.Transaction {

    private lateinit var textSender: TextView
    private lateinit var textReceiver: TextView
    private lateinit var textSenderTitle: TextView
    private lateinit var textReceiverTitle: TextView
    private lateinit var textOrigin: TextView
    private lateinit var textDestination: TextView
    private lateinit var textDetail: TextView
    private lateinit var textStatus: TextView
    private lateinit var textCourier: TextView
    private lateinit var textAwbNum: TextView
    private lateinit var textTitle: TextView
    private lateinit var textSave: TextView

    private lateinit var buttonFinish: MaterialButton

    private lateinit var recyclerView: RecyclerView

    private lateinit var linearLoading: LinearLayout
    private lateinit var scrollView: NestedScrollView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var tracks: ArrayList<AWB>
    private lateinit var awbHistoryAdapter: AwbTrackingAdapter

    private lateinit var binding: ActivityDetailTrackingBinding

    private lateinit var courierCode: String
    private lateinit var courierName: String
    private lateinit var awbNum: String

    private lateinit var realm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailTrackingBinding.inflate(this.layoutInflater)
        super.setContentView(this.binding.root)

        this.extract()
        this.init()
        this.storeToHistory()
    }

    private fun storeToHistory() {
        this.realm.executeTransaction(this)
    }

    private fun extract() {
        this.courierCode = this.intent.getStringExtra(Constant.KEY.COURIER_CODE)!!
        this.courierName = this.intent.getStringExtra(Constant.KEY.COURIER_NAME)!!
        this.awbNum = this.intent.getStringExtra(Constant.KEY.AWB_NUMBER)!!
    }

    private fun init() {
        this.realm = Realm.getDefaultInstance()

        this.textSender = this.binding.textSender
        this.textReceiver = this.binding.textReceiver
        this.textSenderTitle = this.binding.textSenderTitle
        this.textReceiverTitle = this.binding.textReceiverTitle
        this.textOrigin = this.binding.textOrigin
        this.textDestination = this.binding.textDestination
        this.textDetail = this.binding.textDetail
        this.textStatus = this.binding.textStatus
        this.textCourier = this.binding.textCourier
        this.textAwbNum = this.binding.textAwbNumber
        this.textTitle = this.binding.textTitle
        this.textSave = this.binding.textSave
        this.buttonFinish = this.binding.buttonFinish

        this.linearLoading = this.binding.linearLoading
        this.scrollView = this.binding.scrollView
        this.swipeRefreshLayout = this.binding.swipe
        this.recyclerView = this.binding.recyclerView

        this.textAwbNum.text = "No Resi : $awbNum"

        FontsHelper.JOST.lightItalic(this, this.textReceiverTitle, this.textSenderTitle)
        FontsHelper.JOST.medium(
            this,
            this.textSender,
            this.textReceiver,
            this.textDetail,
            this.textStatus,
            this.textCourier,
            this.textTitle,
            this.textSave
        )
        FontsHelper.JOST.regular(this, this.textOrigin, this.textDestination, this.textAwbNum)

        this.tracks = ArrayList()

        if (this.isSaved()){
            this.textSave.visibility = View.GONE
        }

        this.showLoading()

        this.getData()

        this.swipeRefreshLayout.setOnRefreshListener {
            this.getData()
        }

        this.imageBack.setOnClickListener(this)
        this.textSave.setOnClickListener(this)
        this.buttonFinish.setOnClickListener(this)
    }

    private fun getData() {
        this.showLoading()

        if (this.tracks.isNotEmpty()) {
            this.tracks.clear()
        }

        val retrofit = RetrofitBuilder.baseBinderbyte().create(RetrofitService::class.java)
        CompositeDisposable().add(
            retrofit.getAwbDetail(Constant.API.BINDERBYTE_KEY, this.courierCode, this.awbNum)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d("Success Get Data", it.message)
                        this.tracks.add(it.data)

                        this.hideLoading()

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
                        this.awbHistoryAdapter = AwbTrackingAdapter(it.data.history, this)
                        this.recyclerView.adapter = this.awbHistoryAdapter
                        this.recyclerView.layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        //End Get History AWB
                    },
                    {
                        println("StartError")
                        println(it.message)
                        println("EndError")
                    }
                )
        )
    }

    private fun showLoading() {
        this.linearLoading.visibility = View.VISIBLE
        this.scrollView.visibility = View.GONE
        this.swipeRefreshLayout.isRefreshing = true
    }

    private fun hideLoading() {
        this.linearLoading.visibility = View.GONE
        this.scrollView.visibility = View.VISIBLE
        this.swipeRefreshLayout.isRefreshing = false
    }

    override fun onClick(p0: View?) {
        when (p0) {
            this.imageBack -> {
                this.back()
            }
            this.textSave -> {
                this.save()
            }
            this.buttonFinish -> {
                this.finishTracking()
            }
        }
    }
    private fun save() {
        this.realm.executeTransaction{ transaction ->
            val awbLocal = transaction.where<AWBLocal>().equalTo("awbNumber", this.awbNum).findFirst()
            awbLocal!!.isSaved = true
            transaction.copyToRealmOrUpdate(awbLocal)
        }
        this.textSave.visibility = View.GONE
        Toast.makeText(this, "Data Berhasil Disimpan!", Toast.LENGTH_SHORT).show()

    }

    private fun finishTracking() {
        startActivity(
            Intent(
                this,
                HomeActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun back() {
        finish()
    }

    private fun isSaved() : Boolean {
        val awbLocal = realm.where<AWBLocal>().equalTo("awbNumber", this.awbNum).findFirst()

        return awbLocal!!.isSaved
    }

    override fun execute(realm: Realm) {
        var awbLocal = realm.where<AWBLocal>().equalTo("awbNumber", this.awbNum).findFirst()

        if (awbLocal == null){
            awbLocal = realm.createObject(this.awbNum)

            awbLocal.courierCode = this.courierCode
            awbLocal.courierName = this.courierName

            realm.copyToRealmOrUpdate(awbLocal)
        }
    }
}