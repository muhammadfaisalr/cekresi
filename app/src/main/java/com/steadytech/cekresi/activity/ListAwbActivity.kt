package com.steadytech.cekresi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.steadytech.cekresi.R
import com.steadytech.cekresi.adapter.AwbHistoryAdapter
import com.steadytech.cekresi.constant.Constant
import com.steadytech.cekresi.databinding.ActivityListAwbBinding
import com.steadytech.cekresi.helper.FontsHelper
import com.steadytech.cekresi.model.realm.AWBLocal
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class ListAwbActivity : AppCompatActivity(), TextWatcher, View.OnClickListener {

    private lateinit var textTitle: TextView
    private lateinit var textSize: TextView

    private lateinit var imageBack: ImageView

    private lateinit var recyclerView: RecyclerView

    private lateinit var inputAwb: TextInputEditText

    private lateinit var realm: Realm
    private lateinit var awbLocals: RealmResults<AWBLocal>

    private lateinit var binding: ActivityListAwbBinding

    private var isFavouriteMenu: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityListAwbBinding.inflate(this.layoutInflater)
        super.setContentView(this.binding.root)

        this.extract()
        this.init()
    }

    private fun extract() {
        this.isFavouriteMenu = this.intent.getBooleanExtra(Constant.KEY.IS_FAVOURITE_MENU, false)
    }

    private fun init() {
        this.realm = Realm.getDefaultInstance()

        this.textTitle = this.binding.textTitle
        this.textSize = this.binding.textSize
        this.recyclerView = this.binding.recyclerView
        this.inputAwb = this.binding.inputAWB
        this.imageBack = this.binding.imageBack

        if (this.isFavouriteMenu) {
            this.textTitle.text = this.resources.getString(R.string.saved)
            this.awbLocals = this.realm.where<AWBLocal>().equalTo("isSaved", true).findAll()
        } else {
            this.awbLocals = this.realm.where<AWBLocal>().equalTo("isDelete", false).findAll()
        }

        this.recyclerView.adapter = AwbHistoryAdapter(this.awbLocals, this, true)
        this.recyclerView.layoutManager = GridLayoutManager(this, 2)

        this.textSize.text = "${this.awbLocals.size.toString()} Hasil"

        FontsHelper.JOST.medium(this, this.textTitle)
        FontsHelper.JOST.regular(this, this.textSize, this.inputAwb)

        this.inputAwb.addTextChangedListener(this)

        this.imageBack.setOnClickListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        if (s!!.isNotEmpty()) {
            if (this.isFavouriteMenu) {
                this.awbLocals = this.realm.where<AWBLocal>().contains("awbNumber", s.toString())
                    .equalTo("isSaved", true).findAll()
            } else {
                this.awbLocals = this.realm.where<AWBLocal>().contains("awbNumber", s.toString())
                    .equalTo("isDelete", false).findAll()
            }
            this.recyclerView.adapter = AwbHistoryAdapter(this.awbLocals, this, false)
            this.textSize.text = this.awbLocals.size.toString() + " Hasil"
        } else {
            if (this.isFavouriteMenu) {
                this.awbLocals = this.realm.where<AWBLocal>().contains("awbNumber", s.toString())
                    .equalTo("isSaved", true).findAll()
            } else {
                this.awbLocals = this.realm.where<AWBLocal>().contains("awbNumber", s.toString())
                    .equalTo("isDelete", false).findAll()
            }
            this.recyclerView.adapter = AwbHistoryAdapter(this.awbLocals, this, false)
            this.textSize.text = this.awbLocals.size.toString() + " Hasil"
        }
    }

    override fun onClick(v: View?) {
        if (v == this.imageBack) {
            finish()
        }
    }
}