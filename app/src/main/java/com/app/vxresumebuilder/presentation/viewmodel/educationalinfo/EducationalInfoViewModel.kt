package com.app.vxresumebuilder.presentation.viewmodel.educationalinfo

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
class EducationalInfoViewModel @Inject constructor(private val useCase: VxUseCases) : ViewModel() {

    private var _educationDataNew = MutableLiveData<Resource<Long>>()
    var educationDataNew = _educationDataNew

    private var _educationDataExisting = MutableLiveData<Resource<InformationData>>()
    var educationDataExisting = _educationDataExisting

    private var _resumeStatusUpdate = MutableLiveData<Resource<Int>>()
    var resumeStatusUpdate = _resumeStatusUpdate

    private var _eduInfoUpdate = MutableLiveData<Resource<Int>>()
    var eduInfoUpdate = _eduInfoUpdate

    fun saveEducationInformation(resumeId: Long, educationSchool: String, completionYear: String, gpa: String, grade: String) {
        val educationInformation = InformationData(category = Category.EDUCATION, resumeId = resumeId, title = educationSchool, description = completionYear, gpa = gpa,grade = grade)
        viewModelScope.launch {
            useCase.saveEducationalInformation(educationInformation).collect {
                _educationDataNew.postValue(it)
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

    fun getExistingEducationInformation(id: Int) {
        viewModelScope.launch {
            useCase.getInfoDataDetailById(id).collect {
                _educationDataExisting.postValue(it)
            }
        }
    }

    fun updateEducationInformation(informationDataId: Int, educationSchool: String, completionYear: String, gpa: String, grade: String) {
        viewModelScope.launch {
            useCase.updateEducationalInformation(informationDataId,educationSchool,completionYear,gpa,grade).collect {
                _eduInfoUpdate.postValue(it)
            }
        }
    }
}
