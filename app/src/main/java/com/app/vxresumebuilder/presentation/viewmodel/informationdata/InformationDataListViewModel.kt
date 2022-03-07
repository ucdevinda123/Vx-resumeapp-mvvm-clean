package com.app.vxresumebuilder.presentation.viewmodel.informationdata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.vxresumebuilder.data.dto.entity.InformationData
import com.app.vxresumebuilder.data.dto.enumconst.Category
import com.app.vxresumebuilder.domain.VxUseCases
import com.app.vxresumebuilder.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class InformationDataListViewModel @Inject constructor(private val useCase: VxUseCases) : ViewModel() {

    private var _informationDataList = MutableLiveData<Resource<List<InformationData>>>()
    var informationDataList = _informationDataList

    private var _informationData = MutableLiveData<Resource<InformationData>>()
    var informationData = _informationData

    private var _informationDataRemoval = MutableLiveData<Resource<Int>>()
    var informationDataRemoval = _informationDataRemoval

    fun getInformationDataList(resumeId: Long, category: Category) {
        viewModelScope.launch {
            useCase.getExistingInformationDataByResumeId(resumeId, category).collect {
                informationDataList.postValue(it)
            }
        }
    }

    fun getInformationDataDetails(id: Int) {
        viewModelScope.launch {
            useCase.getInfoDataDetailById(id).collect {
                informationData.postValue(it)
            }
        }
    }

    fun removeInformationDataById(id: Int) {
        viewModelScope.launch {
            useCase.removeInformationData(id).collect {
                _informationDataRemoval.postValue(it)
            }
        }
    }
}
