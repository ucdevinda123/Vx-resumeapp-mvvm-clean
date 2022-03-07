package com.app.vxresumebuilder.presentation.viewmodel.perosnalinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.vxresumebuilder.data.dto.entity.PersonalInformation
import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus
import com.app.vxresumebuilder.domain.VxUseCases
import com.app.vxresumebuilder.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(private val useCase: VxUseCases) : ViewModel() {

    private var _personalInfoId = MutableLiveData<Resource<Long>>()
    var personalInfoId = _personalInfoId

    private var _personalInfoUpdate = MutableLiveData<Resource<Int>>()
    var personalInfoUpdate = _personalInfoUpdate

    private var _personalInfoIdExisting = MutableLiveData<Resource<PersonalInformation>>()
    var personalInfoIdExisting = _personalInfoIdExisting

    private var _resumeId = MutableLiveData<Long>()
    var resumeId = _resumeId

    private var _resumeStatusUpdate = MutableLiveData<Resource<Int>>()
    var resumeStatusUpdate = _resumeStatusUpdate

    private var _profileAvatarUrl = MutableLiveData<String>()
    var profileAvatarUrl = _profileAvatarUrl

    fun savePersonalInformation(resumeId: Long, fullName: String, email: String, mobileNumber: String, address: String, profilePic: String) {
        viewModelScope.launch {
            val personalInfo = PersonalInformation(fullName = fullName, resumeId = resumeId, email = email, phoneNumber = mobileNumber, address = address, profilePicture = profilePic)
            useCase.insertPersonInformation(personalInfo).collect {
                _personalInfoId.postValue(it)
            }
        }
    }

    fun getExistingPersonalInformation(resumeId: Long) {
        viewModelScope.launch {
            useCase.getExistingPersonalInfoByResumeId(resumeId).collect {
                _personalInfoIdExisting.postValue(it)
            }
        }
    }

    fun updatePersonalInformation(resumeId: Long, fullName: String, email: String, mobileNumber: String, address: String, profilePic: String) {
        viewModelScope.launch {
            useCase.updatePersonalInformation(fullName, email, mobileNumber, address, resumeId, profilePic).collect {
                _personalInfoUpdate.postValue(it)
            }
        }
    }

    fun updateResumeStatus(resumeId: Long, status: ProgressStatus) {
        viewModelScope.launch {
            useCase.editResume(resumeId = resumeId, status).collect {
                _resumeStatusUpdate.postValue(it)
            }
        }
    }
}
