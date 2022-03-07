package com.app.vxresumebuilder.data.dto.enumconst

enum class ProgressStatus(val nameStatus: String,val stateNum:Int) {
    PENDING("Pending",1),
    COMPLETED_PERSONAL_INFO("Personal Information",2),
    COMPLETED_EDUCATION_INFO("Edu Information",3),
    COMPLETED_EXPERIENCE_INFO("Exp Information",3),
    COMPLETED_PROJECT_INFO("Project Information",4),
    CREATED("Created",4),


}
