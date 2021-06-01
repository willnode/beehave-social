package com.beehavesocial.capstone.view.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beehavesocial.capstone.model.daftar.DaftarUserRequest
import com.beehavesocial.capstone.repository.MainRepository
import com.beehavesocial.capstone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    companion object {
        const val ACTION_DAFTAR_SUCCESS = "DAFTAR_SUCCESS"
        const val ACTION_DAFTAR_FAILED = "DAFTAR_FAILED"
        const val ACTION_DAFTAR_ERROR = "DAFTAR_ERROR"
    }

    val action = MutableLiveData<String>()

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            val userRequest = DaftarUserRequest(
                name = name,
                email = email,
                password = password
            )

            Log.e("datakuku", userRequest.toString())
            when (val response = mainRepository.userRegis(userRequest)) {
                is Resource.Success -> {
                    if (response.data?.status == "success") {
                        action.postValue(ACTION_DAFTAR_SUCCESS)
                    }
                }
                is Resource.Error -> {
                    action.postValue(ACTION_DAFTAR_ERROR)
                }

            }
        }
    }
}