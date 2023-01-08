package com.lafa.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafa.response.RegisterResponse
import com.lafa.model.RegisterModel
import com.lafa.repositories.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {


    private var createMutableLiveData: MutableLiveData<RegisterResponse> = MutableLiveData()
    private var errorMutableLiveData: MutableLiveData<String> = MutableLiveData()


    fun createAccountUser(registerModel: RegisterModel): LiveData<RegisterResponse> {

        viewModelScope.launch {
            try {
                val response = registerRepository.createAccountUser(registerModel)
                if (response.isSuccessful) {
                    response.body().let {
                        createMutableLiveData.postValue(response.body())
                    }
                } else {
                    errorMutableLiveData.postValue(response.errorBody().toString())
                }
            } catch (e: Exception) {
                errorMutableLiveData.postValue(e.message.toString())
            }
        }
        return createMutableLiveData
    }
}

