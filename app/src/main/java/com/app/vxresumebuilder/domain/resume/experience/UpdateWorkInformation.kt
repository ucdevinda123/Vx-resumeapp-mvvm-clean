package com.app.vxresumebuilder.domain.resume.experience

import com.app.vxresumebuilder.domain.repository.VxRepository
import com.app.vxresumebuilder.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateWorkInformation(val repository: VxRepository) {
    suspend operator fun invoke(informationDataId: Int, companyName: String, position: String, duration: String, description: String, isCurrent: Boolean): Flow<Resource<Int>> = repository.updateWorkInfo(informationDataId, companyName, position, duration, description, isCurrent)
}
