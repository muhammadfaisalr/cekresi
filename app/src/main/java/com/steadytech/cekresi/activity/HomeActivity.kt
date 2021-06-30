package com.steadytech.cekresi.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.steadytech.cekresi.constant.Constant
import com.steadytech.cekresi.databinding.ActivityHomeBinding
import com.steadytech.cekresi.helper.FontsHelper
import com.steadytech.cekresi.model.realm.AWBLocal
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var textTitle: TextView
    private lateinit var textTotalHistory: TextView
    private lateinit var textSubtitle: TextView
    private lateinit var textHistory: TextView
    private lateinit var textSaved: TextView
    private lateinit var textTotalSaved: TextView
    private lateinit var textOther: TextView
    private lateinit var textCostCheck: TextView
    private lateinit var textAboutApp: TextView

    private lateinit var cardHistory: CardView
    private lateinit var cardCostCheck: CardView
    private lateinit var cardSaved: CardView

    private lateinit var inputAwb: TextInputEditText
    private lateinit var buttonTrack: MaterialButton

    private lateinit var awbHistory: RealmResults<AWBLocal>
    private lateinit var awbSaved: RealmResults<AWBLocal>
    private lateinit var realm: Realm

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityHomeBinding.inflate(this.layoutInflater)
        super.setContentView(this.binding.root)

        this.init()
    }

    private fun init() {
        this.realm = Realm.getDefaultInstance()

        this.textTitle = this.binding.textTitle
        this.textSubtitle = this.binding.textSubtitle
        this.textOther = this.binding.textOther
        this.textCostCheck = this.binding.textCostCheck
        this.textHistory = this.binding.textHistory
        this.textAboutApp = this.binding.textAboutApp
        this.textTotalHistory = this.binding.textTotalHistory
        this.textSaved = this.binding.textSaved
        this.textTotalSaved = this.binding.textTotalSaved
        this.cardHistory = this.binding.cardHistory
        this.cardSaved = this.binding.cardSaved
        this.cardCostCheck = this.binding.cardCostCheck

        this.inputAwb = this.binding.inputAwbNumber
        this.buttonTrack = this.binding.buttonTrack

        this.awbHistory = this.realm.where<AWBLocal>().equalTo("isDelete", false).findAll()
        this.awbSaved = this.realm.where<AWBLocal>().equalTo("isSaved", true).findAll()

        this.textTotalHistory.text = "${awbHistory.size} Hasil"
        this.textTotalSaved.text = "${awbSaved.size} Hasil"


        FontsHelper.JOST.medium(
            this,
            this.textTitle,
            this.textOther,
            this.textCostCheck,
            this.textHistory,
            this.buttonTrack,
            this.textAboutApp,
            this.textSaved
        )

        FontsHelper.JOST.regular(
            this,
            this.textSubtitle,
            this.inputAwb,
            this.textTotalHistory,
            this.textTotalSaved
        )

        this.buttonTrack.setOnClickListener(this)
        this.cardHistory.setOnClickListener(this)
        this.cardSaved.setOnClickListener(this)
        this.cardCostCheck.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            this.buttonTrack -> {
                this.track()
            }
            this.cardHistory -> {
                this.history()
            }
            this.cardSaved -> {
                this.saved()
            }
            this.cardCostCheck -> {
                this.costCheck()
            }
        }
    }

    private fun costCheck() {
        startActivity(Intent(this, CostCheckActivity::class.java))
    }

    private fun saved() {
        startActivity(Intent(this, ListAwbActivity::class.java).putExtra(Constant.KEY.IS_FAVOURITE_MENU, true))
    }

    private fun history() {
        startActivity(Intent(this, ListAwbActivity::class.java).putExtra(Constant.KEY.IS_FAVOURITE_MENU, false))
    }

    private fun track() {
        val data = this.inputAwb.text.toString()

        if (data != "") {
            startActivity(
                Intent(
                    this,
                    ListCourierActivity::class.java
                ).putExtra(Constant.KEY.AWB_NUMBER, data)
            )
        } else {
            Toast.makeText(this, "Anda belum memasukkan nomor resi", Toast.LENGTH_SHORT).show()
        }
    }
}