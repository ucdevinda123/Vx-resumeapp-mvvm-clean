package com.app.vxresumebuilder.data.repository

import android.content.SharedPreferences
import com.app.vxresumebuilder.data.dto.entity.InformationData
import com.app.vxresumebuilder.data.dto.entity.PersonalInformation
import com.app.vxresumebuilder.data.dto.entity.Resume
import com.app.vxresumebuilder.data.dto.enumconst.Category
import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus
import com.app.vxresumebuilder.data.repository.offline.dao.VxResumeBuilderDao
import com.app.vxresumebuilder.data.repository.offline.db.VxResumeDb
import com.app.vxresumebuilder.domain.repository.VxRepository
import com.app.vxresumebuilder.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RepositoryImpl(vxDatabase: VxResumeDb, private var sharedPreferences: SharedPreferences) : VxRepository {

    private val vxResumeBuilderDao: VxResumeBuilderDao = vxDatabase.vxResumeDao()
    override suspend fun saveResumeInformation(resume: Resume): Flow<Resource<Long>> = flow {
        emit(Resource.Success(vxResumeBuilderDao.insertResumeData(resume)))
    }.flowOn(Dispatchers.IO)

    override suspend fun savePersonalInformation(personalInformation: PersonalInformation) = flow<Resource<Long>> {
        try {
            emit(Resource.Success(vxResumeBuilderDao.insertPersonalData(personalInformation)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getResumeList() = flow<Resource<List<Resume>>> {
        emit(Resource.Success(vxResumeBuilderDao.getResumeList()))
    }.flowOn(Dispatchers.IO)

    override suspend fun removeCv(id: Long) = flow<Resource<Int>> {
        try {
            emit(Resource.Success(vxResumeBuilderDao.removeResumeById(id)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun edit(id: Long, title: String): Flow<Resource<Int>> {
        TODO("Not yet implemented")
    }

    override suspend fun editResumeStatus(id: Long, status: ProgressStatus) = flow<Resource<Int>> {
        try {
            emit(Resource.Success(vxResumeBuilderDao.updateResumeStatus(id, status)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getExistingPersonalInfoByResumeId(resumeId: Long) = flow<Resource<PersonalInformation>> {
        try {
            emit(Resource.Success(vxResumeBuilderDao.getPersonalIdFromResumeId(resumeId)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updatePersonalInformation(fullName: String, email: String, mobileNumber: String, address: String, resumeId: Long, profilePicture: String) = flow<Resource<Int>> {
        try {
            emit(Resource.Success(vxResumeBuilderDao.updatePersonalInformation(fullName, email, mobileNumber, address, resumeId, profilePicture)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getExistingInformationDataByResumeId(
        resumeId: Long,
        category: Category,
    ) = flow<Resource<List<InformationData>>> {
        try {
            emit(Resource.Success(vxResumeBuilderDao.getExistingInformationDataByResumeId(resumeId, category)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getDetailInformationDataById(
        id: Int,
    ) = flow<Resource<InformationData>> {
        try {
            emit(Resource.Success(vxResumeBuilderDao.getDetailInformationDataById(id)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun saveInformationData(informationData: InformationData): Flow<Resource<Long>> = flow {
        emit(Resource.Success(vxResumeBuilderDao.insertInformationData(informationData)))
    }.flowOn(Dispatchers.IO)

    override suspend fun removeInformationData(id: Int) = flow<Resource<Int>> {
        try {
            emit(Resource.Success(vxResumeBuilderDao.removeInformationDataId(id)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateEducationInfo(
        informationDataId: Int,
        educationSchool: String,
        completionYear: String,
        gpa: String,
        grade: String,
    ) = flow<Resource<Int>> {
        try {
            emit(Resource.Success(vxResumeBuilderDao.updateEducationInfo(informationDataId, educationSchool, completionYear, gpa, grade)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateWorkInfo(
        informationDataId: Int,
        companyName: String,
        position: String,
        duration: String,
        description: String,
        isCurrent:Boolean,
    ) = flow<Resource<Int>> {
        try {
            emit(Resource.Success(vxResumeBuilderDao.updateWorkInfo(informationDataId, companyName, position, duration, description,isCurrent)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateProjectInfo(
        informationDataId: Int,
        projectTitle: String,
        clientName: String,
        duration: String,
        description: String,
        role: String,
        isCurrent:Boolean,
    ) = flow<Resource<Int>> {
        try {
            emit(Resource.Success(vxResumeBuilderDao.updateProjectInfo(informationDataId, projectTitle, clientName, duration, description,isCurrent)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}
