package com.beehavesocial.capstone.model.article

import com.google.gson.annotations.SerializedName

data class DetailArticleResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("predicted_rating")
	val predictedRating: Double? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("predicted_raters")
	val predictedRaters: Int? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("viewers")
	val viewers: Int? = null,

	@field:SerializedName("raters")
	val raters: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("predicted_viewers")
	val predictedViewers: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("keyword")
	val keyword: String? = null,

	@field:SerializedName("predicted_score")
	val predictedScore: Double? = null,

	@field:SerializedName("user_rating")
	val userRating: Any? = null
)
