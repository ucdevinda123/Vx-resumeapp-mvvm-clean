package com.app.vxresumebuilder.domain.resume.perosnalinfo

import com.app.vxresumebuilder.domain.repository.VxRepository

class GetExistingPersonalInfoByResumeId(val repository: VxRepository) {
    suspend operator fun invoke(resumeId: Long) = repository.getExistingPersonalInfoByResumeId(resumeId = resumeId)
}
