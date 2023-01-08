package com.lafa.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafa.repositories.QuestionRepository
import com.lafa.response.DataSettingResponse
import com.lafa.response.QuestionResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuestionViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    private var settingMutableLiveData: MutableLiveData<DataSettingResponse> = MutableLiveData()
    private var questionMutableLiveData: MutableLiveData<QuestionResponse> = MutableLiveData()
    private var stringMutableLiveData: MutableLiveData<String> = MutableLiveData()


    fun getSetting(): LiveData<DataSettingResponse> {
        viewModelScope.launch {
            val response = questionRepository.getSettings()
            try {
                if (response.isSuccessful) {
                    response.body().let {
                        settingMutableLiveData.postValue(it)
                    }
                } else {
                    stringMutableLiveData.value = response.errorBody().toString()
                }
            } catch (e: Exception) {
                stringMutableLiveData.value = e.message.toString()
            }
        }

        return settingMutableLiveData
    }

    fun getQuestions(): LiveData<QuestionResponse> {
        viewModelScope.launch {
            val response = questionRepository.getQuestions()
            try {
                if (response.isSuccessful) {
                    response.body().let {
                        questionMutableLiveData.postValue(it)
                    }
                } else {
                    stringMutableLiveData.value = response.errorBody().toString()
                }
            } catch (e: Exception) {
                stringMutableLiveData.value = e.message.toString()
            }

        }

        return questionMutableLiveData
    }
}