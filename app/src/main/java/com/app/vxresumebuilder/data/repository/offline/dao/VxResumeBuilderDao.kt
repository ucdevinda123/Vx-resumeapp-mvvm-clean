package com.app.vxresumebuilder.data.repository.offline.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.vxresumebuilder.data.dto.entity.InformationData
import com.app.vxresumebuilder.data.dto.entity.PersonalInformation
import com.app.vxresumebuilder.data.dto.entity.Resume
import com.app.vxresumebuilder.data.dto.enumconst.Category
import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus

@Dao
interface VxResumeBuilderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResumeData(resume: Resume): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPersonalData(personalInformation: PersonalInformation): Long

    @Query("SELECT * FROM resume")
    fun getResumeList(): List<Resume>

    @Query("DELETE  FROM resume WHERE id=:resumeId")
    fun removeResumeById(resumeId: Long): Int

    @Query("UPDATE resume SET name=:title WHERE id=:resumeId")
    fun updateResumeById(resumeId: Long, title: String): Int

    @Query("UPDATE resume SET status=:status WHERE id=:resumeId")
    fun updateResumeStatus(resumeId: Long, status: ProgressStatus): Int

    @Query("SELECT * FROM personalinformation WHERE resumeId=:resumeId")
    fun getPersonalIdFromResumeId(resumeId: Long): PersonalInformation

    @Query("UPDATE personalinformation SET fullName=:fullName,email=:email,phoneNumber=:mobileNumber,address=:address,profilePicture =:profilePicture WHERE resumeId=:resumeId")
    fun updatePersonalInformation(fullName: String, email: String, mobileNumber: String, address: String, resumeId: Long, profilePicture: String): Int

    @Query("SELECT * FROM informationdata WHERE resume_id=:resumeId AND category=:category")
    fun getExistingInformationDataByResumeId(resumeId: Long, category: Category): List<InformationData>

    @Query("SELECT * FROM informationdata WHERE information_data_id=:id")
    fun getDetailInformationDataById(id: Int): InformationData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInformationData(personalInformation: InformationData): Long

    @Query("DELETE  FROM informationdata WHERE information_data_id=:id")
    fun removeInformationDataId(id: Int): Int

    @Query("UPDATE informationdata SET title=:educationSchool,description=:completionYear,gpa=:gpa,grade=:grade WHERE information_data_id=:informationDataId")
    fun updateEducationInfo(informationDataId: Int, educationSchool: String, completionYear: String, gpa: String, grade: String): Int

    @Query("UPDATE informationdata SET title=:projectTitle,clientname=:clientName,duration=:duration,description=:description,isCurrent=:isCurrent WHERE information_data_id=:informationDataId")
    fun updateProjectInfo(informationDataId: Int, projectTitle: String, clientName: String, duration: String, description: String, isCurrent: Boolean): Int

    @Query("UPDATE informationdata SET title=:companyName,position=:position,duration=:duration,description=:description,isCurrent=:isCurrent WHERE information_data_id=:informationDataId")
    fun updateWorkInfo(informationDataId: Int, companyName: String, position: String, duration: String, description: String, isCurrent: Boolean): Int
}
