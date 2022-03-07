package com.app.vxresumebuilder.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.vxresumebuilder.data.dto.entity.Resume
import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus
import com.app.vxresumebuilder.domain.VxUseCases
import com.app.vxresumebuilder.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeCreationViewModel @Inject constructor(private val useCase: VxUseCases) : ViewModel() {

    private var _resumeId = MutableLiveData<Resource<Long>>()
    var resumeId = _resumeId

    private var _resumeDataList = MutableLiveData<Resource<List<Resume>>>()
    var resumeDataList = _resumeDataList

    private var _resumeRemoval = MutableLiveData<Resource<Int>>()
    var resumeRemoval = _resumeRemoval

    fun createResume(title: String, description: String) {
        viewModelScope.launch {
            val resume = Resume(name = title, status = ProgressStatus.PENDING, descriptionOfYou = description)
            useCase.insertResumeUseCase(resume).collect {
                _resumeId.postValue(it)
            }
        }
    }

    fun getResumeDataList() {
        viewModelScope.launch {
            useCase.getResumeList().collect {
                _resumeDataList.postValue(it)
            }
        }
    }

    fun removeResumeById(id: Long) {
        viewModelScope.launch {
            useCase.removeResume(id).collect {
                _resumeRemoval.postValue(it)
            }
        }
    }
}
