package com.app.vxresumebuilder.presentation.viewmodel.viewer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.vxresumebuilder.data.dto.entity.InformationData
import com.app.vxresumebuilder.data.dto.entity.PersonalInformation
import com.app.vxresumebuilder.data.dto.model.ResumeData
import com.app.vxresumebuilder.domain.VxUseCases
import com.app.vxresumebuilder.util.Resource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ResumeViewerViewModel @Inject constructor(
    private val useCase: VxUseCases,
    private val gson: Gson
) : ViewModel() {

    private var _personalInfoIdExisting = MutableLiveData<Resource<PersonalInformation>>()
    var personalInfoIdExisting = _personalInfoIdExisting

    private var _educationDataExisting = MutableLiveData<Resource<InformationData>>()
    var educationDataExisting = _educationDataExisting

    private var _resumeData = MutableLiveData<Resource<String>>()
    var resumeData = _resumeData

    fun getExistingPersonalInformation(resumeId: Long) {
        viewModelScope.launch {
            useCase.getExistingPersonalInfoByResumeId(resumeId).collect {
                generateWebViewData(it)
            }
        }
    }

    private fun generateWebViewData(personalInfo: Resource<PersonalInformation>) {
        try {
            var resumeData = ResumeData(personalInfo.data?.profilePicture, personalInfo.data?.fullName, personalInfo.data?.phoneNumber, personalInfo.data?.email, personalInfo.data?.address)
            val resumeDataJson = gson.toJson(resumeData)
            val resumeDataJsonObject = JSONObject(resumeDataJson)
            val resumeDataString = resumeDataJsonObject.toString()
            _resumeData.postValue(Resource.Success(resumeDataString))
        } catch (e: Exception) {
            _resumeData.postValue(Resource.Error(e.toString()))
        }
    }

    fun getExistingEducationInformation(id: Int) {
        viewModelScope.launch {
            useCase.getInfoDataDetailById(id).collect {
                _educationDataExisting.postValue(it)
            }
        }
    }
}
