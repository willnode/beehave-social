package com.beehavesocial.capstone.model.profile


import com.google.gson.annotations.SerializedName

data class ProfileUser(
    val email: String?,
    val id: Int?,
    val name: String?,
    val status: String?
)