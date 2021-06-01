package com.beehavesocial.capstone.model.login


import com.google.gson.annotations.SerializedName

data class LoginUserResponse(
    val status: String?,
    val token: String?
)