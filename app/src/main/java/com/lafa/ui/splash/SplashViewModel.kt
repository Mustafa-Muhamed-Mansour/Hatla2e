package com.lafa.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class SplashViewModel @Inject constructor(

) : ViewModel() {


    private val booleanMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()


    fun getAnimation(): LiveData<Boolean> {
        viewModelScope.launch {
            delay(5000)
            booleanMutableLiveData.postValue(true)
        }
        return booleanMutableLiveData
    }
}