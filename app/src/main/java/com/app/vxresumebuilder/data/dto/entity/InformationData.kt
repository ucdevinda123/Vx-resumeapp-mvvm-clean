package com.app.vxresumebuilder.data.dto.entity

import androidx.room.*
import com.app.vxresumebuilder.data.dto.enumconst.Category

@Entity
data class InformationData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "information_data_id")
    val id: Int = 0,
    val category: Category,
    @ColumnInfo(name = "resume_id")
    val resumeId: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "from")
    val from: String = "",
    @ColumnInfo(name = "to")
    val to: String = "",
    @ColumnInfo(name = "gpa")
    val gpa: String? = "",
    @ColumnInfo(name = "grade")
    val grade: String? = "",
    @ColumnInfo(name = "position")
    val position: String = "",
    @ColumnInfo(name = "clientname")
    val clientName: String = "",
    @ColumnInfo(name = "duration")
    val duration: String = "",
    @ColumnInfo(name = "isCurrent")
    val isCurrent: Boolean = false,
)
