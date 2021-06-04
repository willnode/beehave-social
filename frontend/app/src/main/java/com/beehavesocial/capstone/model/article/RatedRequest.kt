package com.beehavesocial.capstone.model.article


import com.google.gson.annotations.SerializedName

data class RatedRequest(
    val action: String="rate",
    val rating: Int?
)