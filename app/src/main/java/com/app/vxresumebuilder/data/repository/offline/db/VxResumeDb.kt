package com.app.vxresumebuilder.data.repository.offline.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.vxresumebuilder.data.dto.entity.InformationData
import com.app.vxresumebuilder.data.dto.entity.PersonalInformation
import com.app.vxresumebuilder.data.dto.entity.Resume
import com.app.vxresumebuilder.data.repository.offline.dao.VxResumeBuilderDao

@Database(entities = [Resume :: class, PersonalInformation::class, InformationData::class], version = 1, exportSchema = false)
abstract class VxResumeDb : RoomDatabase() {
    abstract fun vxResumeDao(): VxResumeBuilderDao
}
