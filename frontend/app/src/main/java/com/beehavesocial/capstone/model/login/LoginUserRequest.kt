package com.beehavesocial.capstone.model.login


import com.google.gson.annotations.SerializedName

data class LoginUserRequest(
    val username: String?,
    val password: String?

)