package com.beehavesocial.capstone.view.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beehavesocial.capstone.model.login.LoginUserRequest
import com.beehavesocial.capstone.repository.MainRepository
import com.beehavesocial.capstone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    companion object {
        const val ACTION_LOGIN_SUCCESS = "LOGIN_SUCCESS"
        const val ACTION_LOGIN_FAILED = "LOGIN_FAILED"
        const val ACTION_LOGIN_ERROR = "LOGIN_ERROR"
    }

    val action = MutableLiveData<String>()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val daftarUserRequest = LoginUserRequest(
                username = username,
                password = password
            )
            Log.e("datakuku", daftarUserRequest.toString())
            when (val response = mainRepository.userLogin(daftarUserRequest)) {
                is Resource.Success -> {
                    if (response.data?.status == "success") {
                        action.postValue(ACTION_LOGIN_SUCCESS)
                    }
                }
                is Resource.Error -> {
                    action.postValue(ACTION_LOGIN_ERROR)
                }
            }
        }


    }
}