package com.beehavesocial.capstone.repository

import com.beehavesocial.capstone.api.ApiService
import com.beehavesocial.capstone.model.daftar.DaftarUserRequest
import com.beehavesocial.capstone.model.daftar.DaftarUserResponse
import com.beehavesocial.capstone.model.login.LoginUserRequest
import com.beehavesocial.capstone.model.login.LoginUserResponse
import com.beehavesocial.capstone.model.profile.ProfileUser
import com.beehavesocial.capstone.utils.Resource
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun userRegis(daftarUserRequest: DaftarUserRequest): Resource<DaftarUserResponse> {
        apiService.userRegister(daftarUserRequest).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(response.message())
        }
    }

    suspend fun userLogin(loginUserRequest: LoginUserRequest): Resource<LoginUserResponse> {
        apiService.userLogin(loginUserRequest).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(response.message())
        }
    }

    suspend fun getProfileUser(bearer: String): Resource<ProfileUser> {
        apiService.profileUser(bearer).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { return Resource.Success(it) }
            }
            return Resource.Error(response.message())
        }
    }

}