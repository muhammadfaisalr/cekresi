package com.steadytech.cekresi.model.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
Created By Faisal | Steady Tech.
------------ 29-12-2020 ------------
-------- Cek Resi --------
 **/

open class AWBLocal(
    @PrimaryKey
    var awbNumber : String = "",
    var courierCode : String = "",
    var courierName : String= "",
    var isSaved : Boolean = false,
    var isDelete : Boolean = false
) : RealmObject()