package com.beehavesocial.capstone.api

import com.beehavesocial.capstone.model.article.ArticleResponse
import com.beehavesocial.capstone.model.article.DetailArticleResponse
import com.beehavesocial.capstone.model.article.RatedRequest
import com.beehavesocial.capstone.model.daftar.DaftarUserRequest
import com.beehavesocial.capstone.model.daftar.DaftarUserResponse
import com.beehavesocial.capstone.model.login.LoginUserRequest
import com.beehavesocial.capstone.model.login.LoginUserResponse
import com.beehavesocial.capstone.model.profile.ProfileUser
import com.bumptech.glide.load.engine.Resource
import retrofit2.Response
import retrofit2.http.*

interface
ApiService {

    @GET("auth/info")
    suspend fun profileUser(
        @Header("Authorization") token: String
    ): Response<ProfileUser>

    @GET("wall")
    suspend fun article(
    ): Response<ArticleResponse>

    @GET("wall/{id}")
    suspend fun articleDetail(@Path("id") id: Int): Response<DetailArticleResponse>

    @POST("auth/register")
    suspend fun userRegister(
        @Body daftarUserRequest: DaftarUserRequest
    ): Response<DaftarUserResponse>

    @POST("auth/login")
    suspend fun userLogin(
        @Body loginUserRequest: LoginUserRequest
    ): Response<LoginUserResponse>

    @POST("wall/{id}")
    suspend fun rated(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body RatedRequest: RatedRequest
    ): Response<DetailArticleResponse>


}