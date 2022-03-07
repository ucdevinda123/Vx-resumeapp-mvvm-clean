package com.app.vxresumebuilder.domain.repository

import com.app.vxresumebuilder.data.dto.entity.InformationData
import com.app.vxresumebuilder.data.dto.entity.PersonalInformation
import com.app.vxresumebuilder.data.dto.entity.Resume
import com.app.vxresumebuilder.data.dto.enumconst.Category
import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus
import com.app.vxresumebuilder.util.Resource
import kotlinx.coroutines.flow.Flow

interface VxRepository {
    suspend fun saveResumeInformation(resume: Resume): Flow<Resource<Long>>
    suspend fun savePersonalInformation(personalInformation: PersonalInformation): Flow<Resource<Long>>

    suspend fun getResumeList(): Flow<Resource<List<Resume>>>
    suspend fun removeCv(id: Long): Flow<Resource<Int>>
    suspend fun edit(id: Long, title: String): Flow<Resource<Int>>
    suspend fun editResumeStatus(id: Long, status: ProgressStatus): Flow<Resource<Int>>
    suspend fun getExistingPersonalInfoByResumeId(resumeId: Long): Flow<Resource<PersonalInformation>>
    suspend fun updatePersonalInformation(fullName: String, email: String, mobileNumber: String, address: String, resumeId: Long, profilePicture: String): Flow<Resource<Int>>
    suspend fun getExistingInformationDataByResumeId(resumeId: Long, category: Category): Flow<Resource<List<InformationData>>>
    suspend fun getDetailInformationDataById(id: Int): Flow<Resource<InformationData>>

    suspend fun saveInformationData(informationData: InformationData): Flow<Resource<Long>>
    suspend fun removeInformationData(id: Int): Flow<Resource<Int>>

    suspend fun updateEducationInfo(informationDataId: Int, educationSchool: String, completionYear: String, gpa: String, grade: String): Flow<Resource<Int>>
    suspend fun updateWorkInfo(informationDataId: Int, companyName: String, position: String, duration: String, description: String, isCurrent: Boolean): Flow<Resource<Int>>
    suspend fun updateProjectInfo(informationDataId: Int, projectTitle: String, clientName: String, duration: String, description: String, role: String, isCurrent: Boolean): Flow<Resource<Int>>
}
