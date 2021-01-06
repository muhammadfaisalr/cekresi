package com.steadytech.cekresi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.steadytech.cekresi.R
import com.steadytech.cekresi.constant.Constant
import com.steadytech.cekresi.fragment.CheckAwbFragment
import com.steadytech.cekresi.fragment.FavoriteFragment
import com.steadytech.cekresi.fragment.HistoryFragment
import com.steadytech.cekresi.helper.FontsHelper

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    lateinit var tabLayout : TabLayout
    lateinit var frameLayout : FrameLayout

    lateinit var textTitle : TextView
    lateinit var textSubtitle : TextView

    lateinit var fragment : Fragment
    lateinit var fragmentManager: FragmentManager
    lateinit var fragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_main)

        this.init()
    }

    private fun init() {
        this.textTitle = findViewById(R.id.textTitle)
        this.textTitle.typeface = FontsHelper.JOST.medium(this)

        this.textSubtitle = findViewById(R.id.textSubtitle)
        this.textSubtitle.typeface = FontsHelper.JOST.regular(this)

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
        var bundle = Bundle()
        if (tab.position == 0){
            this.fragment = CheckAwbFragment()
        }else if (tab.position == 1){
            this.fragment = HistoryFragment()
        }else if (tab.position == 2){
            this.fragment = FavoriteFragment()
        }

        val manager : FragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.commit()
    }
}   