package com.database

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Teacher(
    var id:String?=null,
    var name:String? = null,
    var imageUrl:String? = null,
    var description:String? = null,
    var iconSpinner:String?=null,
    var textSpinner:String?=null,
    var checked : Boolean? = false,
    var price:String?=null,
    var roomNumber:String?=null,
    var bathNumber:String?=null,
    var parkingNumber:String?=null,
    @get:Exclude
    @set:Exclude
    var key:String? = null

) : Parcelable {

}