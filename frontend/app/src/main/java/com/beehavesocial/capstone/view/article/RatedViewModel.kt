package com.beehavesocial.capstone.view.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beehavesocial.capstone.model.article.RatedRequest
import com.beehavesocial.capstone.repository.MainRepository
import com.beehavesocial.capstone.utils.Constant
import com.beehavesocial.capstone.utils.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatedViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    companion object {
        const val ACTION_RATED_SUCCESS = "RATED_SUCCESS"
        const val ACTION_RATED_FAILED = "RATED_FAILED"
        const val ACTION_RATED_ERROR = "RATED_ERROR"
    }

    val action = MutableLiveData<String>()


    fun rated(id:Int, rating:Int){
        viewModelScope.launch {

            val ratedRequest = RatedRequest(
                rating = rating
            )

        when( val response = mainRepository.rated(id,"Bearer "+Constant.BEARER,ratedRequest)){
            is Resource.Success ->{
                if (response.data?.status == "success"){
                    action.postValue(ACTION_RATED_SUCCESS)
                }
            }
        }
        }
    }
}