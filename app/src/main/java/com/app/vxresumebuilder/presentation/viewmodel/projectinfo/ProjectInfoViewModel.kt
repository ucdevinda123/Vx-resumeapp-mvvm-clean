package com.app.vxresumebuilder.presentation.viewmodel.projectinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.vxresumebuilder.data.dto.entity.InformationData
import com.app.vxresumebuilder.data.dto.enumconst.Category
import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus
import com.app.vxresumebuilder.domain.VxUseCases
import com.app.vxresumebuilder.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectInfoViewModel @Inject constructor(private val useCase: VxUseCases) : ViewModel() {

    private var _projectInformationDataNew = MutableLiveData<Resource<Long>>()
    var projectInformationDataNew = _projectInformationDataNew

    private var _projectInformationDataExisting = MutableLiveData<Resource<InformationData>>()
    var projectInformationDataExisting = _projectInformationDataExisting

    private var _resumeStatusUpdate = MutableLiveData<Resource<Int>>()
    var resumeStatusUpdate = _resumeStatusUpdate

    private var _projectInformationUpdate = MutableLiveData<Resource<Int>>()
    var projectInformationUpdate = _projectInformationUpdate

    fun saveProjectInformation(resumeId: Long, projectTitle: String, clientName: String, duration: String, description: String, role: String, isCurrent: Boolean) {
        val workInformation = InformationData(category = Category.PROJECT_DETAILS,clientName=clientName, resumeId = resumeId, title = projectTitle, description = description, position = role, duration = duration, isCurrent = isCurrent)
        viewModelScope.launch {
            useCase.saveWorkInformation(workInformation).collect {
                _projectInformationDataNew.postValue(it)
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

    fun getExistingProjectInformation(id: Int) {
        viewModelScope.launch {
            useCase.getInfoDataDetailById(id).collect {
                _projectInformationDataExisting.postValue(it)
            }
        }
    }

    fun updateProjectInformation(informationDataId: Int, projectTitle: String, clientName: String, duration: String, description: String, role: String, isCurrent: Boolean) {
        viewModelScope.launch {
            useCase.updateProjectInformation(informationDataId, projectTitle, clientName, duration, description,role, isCurrent).collect {
                _projectInformationUpdate.postValue(it)
            }
        }
    }
}
