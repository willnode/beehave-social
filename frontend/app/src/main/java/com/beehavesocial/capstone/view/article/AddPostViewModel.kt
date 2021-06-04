package com.beehavesocial.capstone.view.article

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beehavesocial.capstone.model.article.AddPostRequest
import com.beehavesocial.capstone.repository.MainRepository
import com.beehavesocial.capstone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel(){
    companion object {
        const val ACTION_INPUT_SUCCESS = "INPUT_SUCCESS"
        const val ACTION_INPUT_FAILED = "INPUT_FAILED"
        const val ACTION_INPUT_ERROR = "INPUT_ERROR"
    }

    val action = MutableLiveData<String>()

    fun addPost(title: String, content: String, source: String){
        viewModelScope.launch {
            val userRequest = AddPostRequest(
                title = title,
                content = content,
                source = source
            )

            Log.e("datakuku", userRequest.toString())
            when(val response = mainRepository.addPost(userRequest)){
                is Resource.Success -> {
                    if(response.data?.status == "success")
                      action.postValue(ACTION_INPUT_SUCCESS)
                }
                is Resource.Error -> {
                    action.postValue(ACTION_INPUT_ERROR)
                }
            }
        }
    }
}