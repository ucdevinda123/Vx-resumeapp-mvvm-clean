package com.app.vxresumebuilder.domain.personalinfo

import com.app.vxresumebuilder.data.dto.entity.PersonalInformation
import com.app.vxresumebuilder.domain.repository.VxRepository
import com.app.vxresumebuilder.util.Resource
import kotlinx.coroutines.flow.Flow

class SavePersonalInfoUseCase(val repository: VxRepository) {
    suspend operator fun invoke(personalInformation: PersonalInformation): Flow<Resource<Long>> = repository.savePersonalInformation(personalInformation)
}
