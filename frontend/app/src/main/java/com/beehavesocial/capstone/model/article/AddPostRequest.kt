package com.beehavesocial.capstone.model.article

import com.google.gson.annotations.SerializedName

data class AddPostRequest (

    val title: String? = "",

    val content: String? = "",

    val source: String? = ""
)