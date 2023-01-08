package com.lafa.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafa.repositories.SettingRepository
import com.lafa.response.ContactResponse
import com.lafa.response.LoginResponse
import com.lafa.response.LogoutResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {

    private var loginMutableLiveData: MutableLiveData<LoginResponse> = MutableLiveData()
    private var logoutMutableLiveData: MutableLiveData<LogoutResponse> = MutableLiveData()
    private var contactMutableLiveData: MutableLiveData<ContactResponse> = MutableLiveData()
    private var stringMutableLiveData: MutableLiveData<String> = MutableLiveData()


    fun profileUser(authToken: String): LiveData<LoginResponse> {
        viewModelScope.launch {
            val response = settingRepository.profileUser(authToken)
            try {
                if (response.isSuccessful) {
                    response.body().let {
                        loginMutableLiveData.postValue(it)
                    }
                } else {
                    stringMutableLiveData.value = response.errorBody().toString()
                }
            } catch (e: Exception) {
                stringMutableLiveData.value = e.message.toString()
            }
        }
        return loginMutableLiveData
    }

    fun logoutUser(authTokenLogout: String): LiveData<LogoutResponse> {
        viewModelScope.launch {
            val response = settingRepository.logoutUser(authTokenLogout)
            if (response.isSuccessful) {
                response.body().let {
                    logoutMutableLiveData.postValue(it)
                }
            } else {
                stringMutableLiveData.value = response.errorBody().toString()
            }
        }
        return logoutMutableLiveData
    }

    fun getContact(): LiveData<ContactResponse> {
        viewModelScope.launch {
            val response = settingRepository.getContact()
            try {
                if (response.isSuccessful) {
                    response.body().let {
                        contactMutableLiveData.postValue(it)
                    }
                } else {
                    stringMutableLiveData.value = response.errorBody().toString()
                }
            } catch (e: Exception) {
                stringMutableLiveData.value = e.message.toString()
            }
        }
        return contactMutableLiveData
    }
}