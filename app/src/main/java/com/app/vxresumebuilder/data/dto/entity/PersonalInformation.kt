package com.app.vxresumebuilder.data.dto.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonalInformation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "serverId")
    val serverId: Long = 0,
    @ColumnInfo(name = "resumeId")
    val resumeId: Long,
    @ColumnInfo(name = "fullName")
    val fullName: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "profilePicture")
    val profilePicture: String,
)