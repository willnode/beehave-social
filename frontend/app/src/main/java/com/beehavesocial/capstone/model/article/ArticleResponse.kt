package com.beehavesocial.capstone.model.article

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

	@field:SerializedName("data")
	val data: ArrayList<DataItem>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("viewers")
	val viewers: Int? = null,

	@field:SerializedName(
		"raters")
	val raters: Int? = null,

	@field:SerializedName("rating")
	val rating: Float? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("excerpt")
	val excerpt: String? = null
)
