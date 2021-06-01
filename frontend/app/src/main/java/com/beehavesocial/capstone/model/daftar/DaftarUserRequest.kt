package com.beehavesocial.capstone.model.daftar

import com.google.gson.annotations.SerializedName

data class DaftarUserRequest(
    @SerializedName("name")
    var name : String? = "",
    @SerializedName("email")
    var email: String?="",
    @SerializedName("password")
    var password: String?="",
)
