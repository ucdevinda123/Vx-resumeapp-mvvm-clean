package com.app.vxresumebuilder.data.dto.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus

@Entity
data class Resume(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "serverId")
    val serverId: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val descriptionOfYou: String,
    @ColumnInfo(name = "status")
    val status: ProgressStatus,
)
