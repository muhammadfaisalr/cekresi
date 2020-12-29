package com.steadytech.cekresi.model.awb

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**

Created by : Steady Tech

 **/

@Parcelize
data class AWBDetail(
    var origin : String,
    var destination : String,
    var shipper : String,
    var receiver : String
) : Parcelable