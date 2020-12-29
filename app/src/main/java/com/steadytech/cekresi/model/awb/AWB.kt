package com.steadytech.cekresi.model.awb

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**

Created By : Steady Tech.

 **/

@Parcelize
data class AWB(
    var summary : AWBSummary,
    var detail : AWBDetail,
    var history : ArrayList<AWBHistory>
) : Parcelable