package com.steadytech.cekresi.model.awb

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**

Created by : Steady Tech

 **/

@Parcelize
data class AWBHistory(
    var date : String,
    var desc : String,
    var location : String
) : Parcelable