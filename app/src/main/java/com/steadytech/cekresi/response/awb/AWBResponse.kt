package com.steadytech.cekresi.response.awb

import com.steadytech.cekresi.model.awb.AWB

/**

Created by : Steady Tech.

 **/

data class AWBResponse(
    var status : Int,
    var message : String,
    var data : AWB
)