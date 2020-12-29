package com.steadytech.cekresi.model.local

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

/**

Created By : Steady Tech.

 **/

@Parcelize
data class ListCourier(
    var code : String,
    var name : String) : Parcelable