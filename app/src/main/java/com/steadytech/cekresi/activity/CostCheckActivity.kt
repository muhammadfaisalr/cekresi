package com.steadytech.cekresi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.minibugdev.sheetselection.SheetSelection
import com.minibugdev.sheetselection.SheetSelectionItem
import com.steadytech.cekresi.R
import com.steadytech.cekresi.builder.RetrofitBuilder
import com.steadytech.cekresi.constant.Constant
import com.steadytech.cekresi.databinding.ActivityCostCheckBinding
import com.steadytech.cekresi.helper.FontsHelper
import com.steadytech.cekresi.response.rajaongkir.GeneralResponse
import com.steadytech.cekresi.service.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io

class CostCheckActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var inputProvince : TextInputEditText

    private lateinit var binding: ActivityCostCheckBinding

    private lateinit var provinces : ArrayList<SheetSelectionItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityCostCheckBinding.inflate(this.layoutInflater)
        super.setContentView(this.binding.root)


        this.init()

        this.data()
    }

    private fun data() {
        val retrofit = RetrofitBuilder.baseRajaOngkir().create(RetrofitService::class.java)

        CompositeDisposable().add(
            retrofit.getProvince(Constant.API.RAJA_ONGKIR_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(io())
                .subscribe(
                    {
//                      Do On Response OK
                        this.provinceResponseOk(it)

                    },
                    {
//                      Do On Response Error
                        it.printStackTrace()
                        Toast.makeText(this, "Response ERROR " + it.message, Toast.LENGTH_SHORT).show()
                    }
                )
        )
    }

    private fun provinceResponseOk(generalResponse: GeneralResponse) {
        for (data in generalResponse.rajaOngkirResponse.resultsResponse){
            this.provinces.add(SheetSelectionItem(data.provinceId, data.provinceName))
        }

        this.inputProvince.setOnClickListener(this)


    }

    private fun init() {

        this.inputProvince = this.binding.inputProvince

        this.provinces = ArrayList()

        FontsHelper.JOST.regular(this, this.inputProvince)
    }

    override fun onClick(v: View?) {
        if (v == this.inputProvince){
            SheetSelection.Builder(this)
                .theme(R.style.CustomSheetSelection)
                .title(this.resources.getString(R.string.select_courier))
                .items(this.provinces)
                .showDraggedIndicator(true)
                .searchEnabled(true)
                .searchNotFoundText("Ekspedisi Tidak Tersedia!")
                .onItemClickListener { item, position ->
                    Toast.makeText(this, item.value, Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }
}