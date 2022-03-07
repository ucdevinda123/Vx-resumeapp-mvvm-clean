package com.app.vxresumebuilder.domain.resume

import com.app.vxresumebuilder.domain.repository.VxRepository

class RemoveResume(val repository: VxRepository) {
    suspend operator fun invoke(resumeId: Long) = repository.removeCv(id = resumeId)
}
