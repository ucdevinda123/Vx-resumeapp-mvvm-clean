package com.app.vxresumebuilder.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.app.vxresumebuilder.data.repository.RepositoryImpl
import com.app.vxresumebuilder.data.repository.offline.db.VxResumeDb
import com.app.vxresumebuilder.domain.VxUseCases
import com.app.vxresumebuilder.domain.personalinfo.SavePersonalInfoUseCase
import com.app.vxresumebuilder.domain.repository.VxRepository
import com.app.vxresumebuilder.domain.resume.*
import com.app.vxresumebuilder.domain.resume.education.SaveEducationalInformation
import com.app.vxresumebuilder.domain.resume.education.UpdateEducationalInformation
import com.app.vxresumebuilder.domain.resume.experience.SaveWorkInformation
import com.app.vxresumebuilder.domain.resume.experience.UpdateWorkInformation
import com.app.vxresumebuilder.domain.resume.informationdata.GetExistingInformationDataByResumeId
import com.app.vxresumebuilder.domain.resume.informationdata.GetInfoDataDetailById
import com.app.vxresumebuilder.domain.resume.informationdata.RemoveInformationData
import com.app.vxresumebuilder.domain.resume.perosnalinfo.GetExistingPersonalInfoByResumeId
import com.app.vxresumebuilder.domain.resume.perosnalinfo.UpdatePersonalInformation
import com.app.vxresumebuilder.domain.resume.projectdetails.SaveProjectInformation
import com.app.vxresumebuilder.domain.resume.projectdetails.UpdateProjectInformation
import com.app.vxresumebuilder.util.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class VxDiModule {

    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext context: Context): VxResumeDb {
        return Room.databaseBuilder(context, VxResumeDb::class.java, Constants.DB_NAME).build()
    }

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideGlide(@ApplicationContext context: Context): RequestManager = Glide.with(context)

    @Provides
    @Singleton
    fun provideRepository(
        roomDatabase: VxResumeDb,
        sharedPreferences: SharedPreferences,
    ): VxRepository {
        return RepositoryImpl(roomDatabase, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideGson() : Gson{
        return Gson()
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: VxRepository): VxUseCases {
        return VxUseCases(
            insertPersonInformation = SavePersonalInfoUseCase(repository),
            insertResumeUseCase = SaveResumeUseCase(repository),
            getResumeList = GetResumeList(repository),
            removeResume = RemoveResume(repository),
            getExistingPersonalInfoByResumeId = GetExistingPersonalInfoByResumeId(repository),
            updatePersonalInformation = UpdatePersonalInformation(repository),
            editResume = EditResume(repository),
            getExistingInformationDataByResumeId = GetExistingInformationDataByResumeId(repository),
            getInfoDataDetailById = GetInfoDataDetailById(repository),
            saveEducationalInformation = SaveEducationalInformation(repository),
            removeInformationData = RemoveInformationData(repository),
            updateEducationalInformation = UpdateEducationalInformation(repository),
            saveWorkInformation= SaveWorkInformation(repository),
            updateWorkInformation= UpdateWorkInformation(repository),
            saveProjectInformation= SaveProjectInformation(repository),
            updateProjectInformation= UpdateProjectInformation(repository),
        )
    }
}
