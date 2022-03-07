package com.app.vxresumebuilder.presentation.viewmodel.workinfo

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
class WorkInfoViewModel @Inject constructor(private val useCase: VxUseCases) : ViewModel() {

    private var _workInforDataNew = MutableLiveData<Resource<Long>>()
    var workInforDataNew = _workInforDataNew

    private var _workInfoDataExisting = MutableLiveData<Resource<InformationData>>()
    var workInfoDataExisting = _workInfoDataExisting

    private var _resumeStatusUpdate = MutableLiveData<Resource<Int>>()
    var resumeStatusUpdate = _resumeStatusUpdate

    private var _workInfoUpdate = MutableLiveData<Resource<Int>>()
    var workInfoUpdate = _workInfoUpdate

    fun saveWorkInformation(resumeId: Long, companyName: String, position: String, duration: String, description: String, isCurrent: Boolean) {
        val workInformation = InformationData(category = Category.EXPERIENCE, resumeId = resumeId, title = companyName, description = description, position = position, duration = duration, isCurrent = isCurrent)
        viewModelScope.launch {
            useCase.saveWorkInformation(workInformation).collect {
                _workInforDataNew.postValue(it)
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

    fun getExistingWorkInformation(id: Int) {
        viewModelScope.launch {
            useCase.getInfoDataDetailById(id).collect {
                _workInfoDataExisting.postValue(it)
            }
        }
    }

    fun updateWorkInformation(informationDataId: Int, companyName: String, position: String, duration: String, description: String, isCurrent: Boolean) {
        viewModelScope.launch {
            useCase.updateWorkInformation(informationDataId, companyName, position, duration, description, isCurrent).collect {
                _workInfoUpdate.postValue(it)
            }
        }
    }
}
