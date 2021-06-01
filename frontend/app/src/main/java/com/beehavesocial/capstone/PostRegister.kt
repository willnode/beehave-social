package com.beehavesocial.capstone

import com.google.gson.annotations.SerializedName

data class PostRegister(
    @field:SerializedName("status")
    val status:Boolean,

    @field:SerializedName("token")
    val token:String
)
