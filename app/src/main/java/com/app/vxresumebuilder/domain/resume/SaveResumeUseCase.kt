package com.app.vxresumebuilder.domain.resume

import com.app.vxresumebuilder.data.dto.entity.Resume
import com.app.vxresumebuilder.domain.repository.VxRepository

class SaveResumeUseCase(val repository: VxRepository) {
    suspend operator fun invoke(resume: Resume) = repository.saveResumeInformation(resume)
}
