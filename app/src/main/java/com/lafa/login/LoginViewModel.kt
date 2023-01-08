package com.lafa.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafa.model.LoginModel
import com.lafa.repositories.LoginRepository
import com.lafa.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import java.lang.IllegalArgumentException
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private var loginMutableLiveData: MutableLiveData<LoginResponse> = MutableLiveData()
    private var stringMutableLiveData: MutableLiveData<String> = MutableLiveData()



    fun loginUser(loginModel: LoginModel): LiveData<LoginResponse> {
        viewModelScope.launch {
            try {
                val response = loginRepository.loginUser(loginModel)
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

}