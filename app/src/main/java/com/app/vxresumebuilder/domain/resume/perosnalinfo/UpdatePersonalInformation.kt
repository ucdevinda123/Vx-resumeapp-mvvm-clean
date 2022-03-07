package com.app.vxresumebuilder.domain.resume.perosnalinfo

import com.app.vxresumebuilder.domain.repository.VxRepository

class UpdatePersonalInformation(val repository: VxRepository) {

    suspend operator fun invoke(fullName: String, email: String, mobileNumber: String, address: String, resumeId: Long, profilePicture: String) = repository.updatePersonalInformation(fullName, email, mobileNumber, address, resumeId,profilePicture)
}
