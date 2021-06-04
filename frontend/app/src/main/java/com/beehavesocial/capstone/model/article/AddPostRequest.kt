package com.beehavesocial.capstone.model.article

import com.google.gson.annotations.SerializedName

class AddPostRequest (
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("contet")
    val content: String? = "",
    @SerializedName("source")
    val source: String? = ""
)