package com.beehavesocial.capstone.repository

import com.beehavesocial.capstone.api.ApiService
import com.beehavesocial.capstone.model.article.*

import com.beehavesocial.capstone.model.daftar.DaftarUserRequest
import com.beehavesocial.capstone.model.daftar.DaftarUserResponse
import com.beehavesocial.capstone.model.login.LoginUserRequest
import com.beehavesocial.capstone.model.login.LoginUserResponse
import com.beehavesocial.capstone.model.profile.ProfileUser
import com.beehavesocial.capstone.model.article.AddPostRequest
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

    suspend fun addPost(bearer: String, addPostRequest: AddPostRequest): Resource<AddPostResponse>{
        apiService.addPost(bearer,addPostRequest).let { response ->
            if (response.isSuccessful){
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

    suspend fun rated(
        id: Int,
        bearer: String,
        ratedRequest: RatedRequest
    ): Resource<DetailArticleResponse> {
        apiService.rated(id, bearer, ratedRequest).let { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    return Resource.Success(it)
                }
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

    suspend fun getArticle(): Resource<ArticleResponse> {
        apiService.article().let { response ->
            if (response.isSuccessful) {
                response.body()?.let { article ->
                    return Resource.Success(article)
                }
            }
            return Resource.Error(response.message())
        }
    }

    suspend fun search(keyword:String): Resource<ArticleResponse> {
        apiService.search(keyword).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { search ->
                    return Resource.Success(search)
                }
            }
            return Resource.Error(response.message())
        }
    }

    suspend fun getDetailArticle(bearer: String,id: Int): Resource<DetailArticleResponse> {
        apiService.articleDetail(bearer,id).let { response ->
            if (response.isSuccessful) {
                response.body()?.let { detail ->
                    return Resource.Success(detail)
                }
            }
            return Resource.Error(response.message())
        }
    }



}