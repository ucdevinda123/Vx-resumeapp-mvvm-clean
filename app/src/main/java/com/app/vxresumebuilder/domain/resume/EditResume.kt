package com.app.vxresumebuilder.domain.resume

import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus
import com.app.vxresumebuilder.domain.repository.VxRepository

class EditResume(val repository: VxRepository) {
    suspend operator fun invoke(resumeId: Long, status: ProgressStatus) = repository.editResumeStatus(id = resumeId, status)
}
