package com.beehavesocial.capstone.view.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beehavesocial.capstone.model.article.DataItem
import com.beehavesocial.capstone.repository.MainRepository
import com.beehavesocial.capstone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {


    val responseArticle = MutableLiveData<ArrayList<DataItem>>()
//    val action = MutableLiveData<ArrayList<DataItem>>()

    fun getArticle(): MutableLiveData<ArrayList<DataItem>> {
        viewModelScope.launch {
            val articleResponse: ArrayList<DataItem> = arrayListOf()
            when (val response = mainRepository.getArticle()) {
                is Resource.Success -> {
                    response.data?.data?.forEach { article ->
                        articleResponse.add(article)
                    }
                    responseArticle.postValue(articleResponse)

                }
                is Resource.Error -> {
                    response.message

                }
            }
        }
        return responseArticle
    }

}