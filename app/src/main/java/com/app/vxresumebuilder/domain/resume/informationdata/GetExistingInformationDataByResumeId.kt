package com.app.vxresumebuilder.domain.resume.informationdata

import com.app.vxresumebuilder.data.dto.enumconst.Category
import com.app.vxresumebuilder.domain.repository.VxRepository

class GetExistingInformationDataByResumeId(val repository: VxRepository) {
    suspend operator fun invoke(resumeId: Long, category: Category) = repository.getExistingInformationDataByResumeId(resumeId = resumeId, category = category)
}
