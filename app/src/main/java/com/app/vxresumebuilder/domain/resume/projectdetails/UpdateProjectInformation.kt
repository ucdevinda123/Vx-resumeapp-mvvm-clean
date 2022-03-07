package com.app.vxresumebuilder.domain.resume.projectdetails

import com.app.vxresumebuilder.domain.repository.VxRepository
import com.app.vxresumebuilder.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateProjectInformation(val repository: VxRepository) {
    suspend operator fun invoke(informationDataId: Int, projectTitle: String, clientName: String, duration: String, description: String, role: String, isCurrent: Boolean): Flow<Resource<Int>> = repository.updateProjectInfo(informationDataId, projectTitle, clientName, duration, description, role, isCurrent)
}
