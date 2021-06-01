package com.beehavesocial.capstone.api

import com.beehavesocial.capstone.model.daftar.DaftarUserRequest
import com.beehavesocial.capstone.model.daftar.DaftarUserResponse
import com.beehavesocial.capstone.model.login.LoginUserRequest
import com.beehavesocial.capstone.model.login.LoginUserResponse
import com.beehavesocial.capstone.model.profile.ProfileUser
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("auth/info")
    suspend fun profileUser(
        @Header("Authorization") token: String
    ): Response<ProfileUser>

    @POST("auth/register")
    suspend fun userRegister(
        @Body daftarUserRequest: DaftarUserRequest
    ): Response<DaftarUserResponse>

    @POST("auth/login")
    suspend fun userLogin(
        @Body loginUserRequest: LoginUserRequest
    ):Response<LoginUserResponse>
}