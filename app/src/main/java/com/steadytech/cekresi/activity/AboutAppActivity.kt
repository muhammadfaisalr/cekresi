package com.steadytech.cekresi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.steadytech.cekresi.R
import com.steadytech.cekresi.databinding.ActivityAboutAppBinding

class AboutAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityAboutAppBinding.inflate(this.layoutInflater)
        super.setContentView(this.binding.root)

        this.init()
    }

    private fun init() {

    }
}