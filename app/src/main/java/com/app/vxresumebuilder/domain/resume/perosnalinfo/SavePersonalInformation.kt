package com.app.vxresumebuilder.domain.resume.perosnalinfo

import com.app.vxresumebuilder.data.dto.entity.PersonalInformation
import com.app.vxresumebuilder.domain.repository.VxRepository

class SavePersonalInformation(val repository: VxRepository) {
    suspend operator fun invoke(personalInformation: PersonalInformation) = repository.savePersonalInformation(personalInformation = personalInformation)
}
