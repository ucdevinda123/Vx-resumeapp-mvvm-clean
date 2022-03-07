package com.app.vxresumebuilder.domain

import com.app.vxresumebuilder.domain.personalinfo.SavePersonalInfoUseCase
import com.app.vxresumebuilder.domain.resume.EditResume
import com.app.vxresumebuilder.domain.resume.GetResumeList
import com.app.vxresumebuilder.domain.resume.RemoveResume
import com.app.vxresumebuilder.domain.resume.SaveResumeUseCase
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

open class VxUseCases(
    val insertPersonInformation: SavePersonalInfoUseCase,
    val insertResumeUseCase: SaveResumeUseCase,
    val editResume: EditResume,
    val getResumeList: GetResumeList,
    val removeResume: RemoveResume,
    val getExistingPersonalInfoByResumeId: GetExistingPersonalInfoByResumeId,
    val updatePersonalInformation: UpdatePersonalInformation,
    val getExistingInformationDataByResumeId: GetExistingInformationDataByResumeId,
    val getInfoDataDetailById: GetInfoDataDetailById,
    val saveEducationalInformation: SaveEducationalInformation,
    val removeInformationData: RemoveInformationData,
    val updateEducationalInformation: UpdateEducationalInformation,
    val saveWorkInformation: SaveWorkInformation,
    val updateWorkInformation: UpdateWorkInformation,
    val saveProjectInformation: SaveProjectInformation,
    val updateProjectInformation: UpdateProjectInformation,

)
