package com.app.vxresumebuilder.domain.resume

import com.app.vxresumebuilder.domain.repository.VxRepository

class GetResumeList(val repository: VxRepository) {
    suspend operator fun invoke() = repository.getResumeList()
}
