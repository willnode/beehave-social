package com.beehavesocial.capstone.model.article


import com.google.gson.annotations.SerializedName

data class RatedRequest(
    val action: String?,
    val rating: Int?
)