package com.lafa.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafa.model.RegisterModel
import com.lafa.repositories.ProfileRepository
import com.lafa.response.*
import kotlinx.coroutines.launch
import retrofit2.http.Body
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private var loginMutableLiveData: MutableLiveData<LoginResponse> = MutableLiveData()
    private var updateMutableLiveData: MutableLiveData<UpdateProfileResponse> = MutableLiveData()
    private var changeMutableLiveData: MutableLiveData<ChangePasswordResponse> = MutableLiveData()
    private var stringMutableLiveData: MutableLiveData<String> = MutableLiveData()


    fun profileUser(authToken: String): LiveData<LoginResponse> {
        viewModelScope.launch {
            val response = profileRepository.profileUser(authToken)
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

    fun updateProfile(
        authTokenUpdate: String,
        registerModel: RegisterModel
    ): LiveData<UpdateProfileResponse> {
        viewModelScope.launch {
            val response = profileRepository.updateProfile(authTokenUpdate, registerModel)
            try {
                if (response.isSuccessful) {
                    response.body().let {
                        updateMutableLiveData.postValue(it)
                    }
                } else {
                    stringMutableLiveData.value = response.errorBody().toString()
                }
            } catch (e: Exception) {
                stringMutableLiveData.value = e.message.toString()
            }
        }
        return updateMutableLiveData
    }

    fun changePassword(
        authTokenChange: String,
        changePassword: ChangePassword
    ): LiveData<ChangePasswordResponse> {
        viewModelScope.launch {
            val response = profileRepository.changePassword(authTokenChange, changePassword)
            try {
                if (response.isSuccessful) {
                    response.body().let {
                        changeMutableLiveData.postValue(it)
                    }
                } else {
                    stringMutableLiveData.value = response.errorBody().toString()
                }
            } catch (e: Exception) {
                stringMutableLiveData.value = e.message.toString()
            }
        }
        return changeMutableLiveData
    }
}