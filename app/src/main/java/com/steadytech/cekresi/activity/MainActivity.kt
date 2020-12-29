package com.steadytech.cekresi.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.FragmentTransitionImpl
import com.ahmed3elshaer.selectionbottomsheet.SelectionBuilder
import com.google.android.material.tabs.TabLayout
import com.nestoleh.bottomsheetspinner.BottomSheetSpinner
import com.nestoleh.bottomsheetspinner.adapter.BottomSheetSpinnerAdapter
import com.nestoleh.bottomsheetspinner.adapter.BottomSheetSpinnerItemViewHolder
import com.steadytech.cekresi.R
import com.steadytech.cekresi.builder.RetrofitBuilder
import com.steadytech.cekresi.fragment.CheckAwbFragment
import com.steadytech.cekresi.fragment.HistoryFragment
import com.steadytech.cekresi.service.RetrofitService
import com.tiper.MaterialSpinner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    lateinit var tabLayout : TabLayout
    lateinit var frameLayout : FrameLayout

    lateinit var fragment : Fragment
    lateinit var fragmentManager: FragmentManager
    lateinit var fragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_main)

        this.init()
    }

    private fun init() {
        this.tabLayout = findViewById(R.id.tabs)
        this.frameLayout = findViewById(R.id.frameLayout)
        this.fragment = CheckAwbFragment()
        this.fragmentManager = supportFragmentManager
        this.fragmentTransaction = this.fragmentManager.beginTransaction()
        this.fragmentTransaction.replace(R.id.frameLayout, fragment)
        this.fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        this.fragmentTransaction.commit()

        this.tabLayout.addOnTabSelectedListener(this)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        println("ok")
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        println("ok")
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab!!
        if (tab.position == 0){
            this.fragment = CheckAwbFragment()
        }else if (tab.position == 1){
            this.fragment = HistoryFragment()
        }

        val manager : FragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.commit()
    }
}   