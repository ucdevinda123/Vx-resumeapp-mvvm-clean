package com.app.vxresumebuilder.domain.resume.education

import com.app.vxresumebuilder.data.dto.entity.InformationData
import com.app.vxresumebuilder.domain.repository.VxRepository
import com.app.vxresumebuilder.util.Resource
import kotlinx.coroutines.flow.Flow

class SaveEducationalInformation(val repository: VxRepository) {
    suspend operator fun invoke(informationData: InformationData): Flow<Resource<Long>> = repository.saveInformationData(informationData)
}