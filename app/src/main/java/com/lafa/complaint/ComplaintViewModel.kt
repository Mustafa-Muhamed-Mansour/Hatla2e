package com.lafa.complaint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafa.repositories.ComplaintRepository
import com.lafa.response.DataComplaint
import com.lafa.response.DataComplaintResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class ComplaintViewModel @Inject constructor(
    private val complaintRepository: ComplaintRepository
) : ViewModel() {


    private val complaintMutableLiveData: MutableLiveData<DataComplaintResponse> = MutableLiveData()
    private val stringMutableLiveData: MutableLiveData<String> = MutableLiveData()


    fun addNewComplaint(dataComplaint: DataComplaint): LiveData<DataComplaintResponse> {
        viewModelScope.launch {
            val response = complaintRepository.addNewComplaint(dataComplaint)
            try {
                if (response.isSuccessful) {
                    response.body().let {
                        complaintMutableLiveData.postValue(it)
                    }
                } else {
                    stringMutableLiveData.value = response.errorBody().toString()
                }
            } catch (e: Exception) {
                stringMutableLiveData.value = e.message.toString()
            }
        }
        return complaintMutableLiveData
    }


}