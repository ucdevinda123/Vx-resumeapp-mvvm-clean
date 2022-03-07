package com.app.vxresumebuilder.domain.resume.informationdata

import com.app.vxresumebuilder.domain.repository.VxRepository

class GetInfoDataDetailById(val repository: VxRepository) {
    suspend operator fun invoke(id: Int) = repository.getDetailInformationDataById(id)
}