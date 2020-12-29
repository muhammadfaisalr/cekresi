package com.steadytech.cekresi.model.awb

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**

Created By : Steady Tech.

 **/

@Parcelize
data class AWBSummary(
    var awb : String,
    var courier : String,
    var service : String,
    var status : String,
    var date : String,
    var desc : String,
    var amount : String,
    var weight : String
) : Parcelable