package com.beehavesocial.capstone.view.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beehavesocial.capstone.model.article.DetailArticleResponse
import com.beehavesocial.capstone.repository.MainRepository
import com.beehavesocial.capstone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailArticleViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    val artData = MutableLiveData<DetailArticleResponse>()

    fun getDetailArticle(id:Int){
        viewModelScope.launch {
            when(val response = mainRepository.getDetailArticle(id)){
                is Resource.Success -> {
                    response.data?.let {
                        artData.postValue(it)
                    }
                }
                is Resource.Error -> {
                    response.message
                }
            }
        }
    }
}