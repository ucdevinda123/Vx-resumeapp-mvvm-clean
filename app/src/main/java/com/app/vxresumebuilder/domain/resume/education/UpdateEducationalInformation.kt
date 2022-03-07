package com.app.vxresumebuilder.domain.resume.education

import com.app.vxresumebuilder.domain.repository.VxRepository
import com.app.vxresumebuilder.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateEducationalInformation(val repository: VxRepository) {
    suspend operator fun invoke(informationDataId: Int, educationSchool: String, completionYear: String, gpa: String, grade: String): Flow<Resource<Int>> = repository.updateEducationInfo(informationDataId,educationSchool,completionYear,gpa,grade)
}