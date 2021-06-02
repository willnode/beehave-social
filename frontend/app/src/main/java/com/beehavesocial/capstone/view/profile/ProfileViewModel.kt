package com.beehavesocial.capstone.view.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beehavesocial.capstone.model.profile.ProfileUser
import com.beehavesocial.capstone.repository.MainRepository
import com.beehavesocial.capstone.utils.Constant
import com.beehavesocial.capstone.utils.Resource
import com.beehavesocial.capstone.view.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel  @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val responseProfile = MutableLiveData<ProfileUser>()
    val action = MutableLiveData<String>()



    fun getProfile(){
        viewModelScope.launch {
            when (val response = mainRepository.getProfileUser("Bearer "+Constant.BEARER)) {
                is Resource.Success -> {
                    responseProfile.postValue(response.data!!)
                    action.postValue(LoginViewModel.ACTION_LOGIN_SUCCESS)
                }
                is Resource.Error -> {
                    action.postValue(LoginViewModel.ACTION_LOGIN_ERROR)
                }
            }
        }
    }


}

