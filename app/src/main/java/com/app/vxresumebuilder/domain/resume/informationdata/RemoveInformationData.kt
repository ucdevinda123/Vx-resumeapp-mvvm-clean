package com.app.vxresumebuilder.domain.resume.informationdata

import com.app.vxresumebuilder.domain.repository.VxRepository

class RemoveInformationData(val repository: VxRepository) {
    suspend operator fun invoke(informationDataId: Int) = repository.removeInformationData(id = informationDataId)
}
