package com.app.vxresumebuilder.presentation.ui.fragment.personal

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.vxresumebuilder.R
import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus
import com.app.vxresumebuilder.data.dto.enumconst.WorkFlow
import com.app.vxresumebuilder.databinding.FragmentAddPersonalInformationBinding
import com.app.vxresumebuilder.presentation.viewmodel.perosnalinfo.PersonalInfoViewModel
import com.app.vxresumebuilder.util.Constants
import com.app.vxresumebuilder.util.Constants.DATA_FLOW_FLAG
import com.app.vxresumebuilder.util.Constants.DATA_FLOW_RESUME_ID
import com.app.vxresumebuilder.util.Resource
import com.app.vxresumebuilder.util.Utilities.hasPermissions
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PersonalInformation : Fragment(R.layout.fragment_add_personal_information) {

    private val personalInfoViewModel: PersonalInfoViewModel by viewModels()
    private lateinit var fragmentAddPersonalInformationBinding: FragmentAddPersonalInformationBinding
    private lateinit var activityLauncher: ActivityResultLauncher<Intent>
    @Inject
    lateinit var glideRequestManager: RequestManager
    private lateinit var galleryLaunchIntent: Intent
    private var resumeId: Long? = 0
    private var isNewInsertionDone = false

    companion object {

        var PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        fun newInstance(flow: WorkFlow, resumeId: Long): PersonalInformation {
            return PersonalInformation().apply {
                arguments = Bundle().apply {
                    putSerializable(DATA_FLOW_FLAG, flow)
                    putLong(DATA_FLOW_RESUME_ID, resumeId)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentAddPersonalInformationBinding = FragmentAddPersonalInformationBinding.bind(view)
        initializePersonalInformation()
        registerGalleryLaunchActivityForResult()
    }

    private fun initializePersonalInformation() {
        val workFlow = arguments?.getSerializable(DATA_FLOW_FLAG)
        resumeId = arguments?.getLong(DATA_FLOW_RESUME_ID)

        when (workFlow) {
            WorkFlow.NEW_CREATION -> {
                initObserversForNewDataFlow()
            } else -> {
                initObserversForUpdateFlow()
                resumeId?.let { personalInfoViewModel.getExistingPersonalInformation(it) }
            }
        }

        fragmentAddPersonalInformationBinding.btnNextPersonInfo.setOnClickListener {
            val fullName = fragmentAddPersonalInformationBinding.edtFullName.text.toString()
            val email = fragmentAddPersonalInformationBinding.edtEmail.text.toString()
            val mobile = fragmentAddPersonalInformationBinding.edtMobile.text.toString()
            val address = fragmentAddPersonalInformationBinding.edtAddress.text.toString()
            val profileUrl = personalInfoViewModel.profileAvatarUrl.value
            val isValid = validateFields(fullName, email, mobile, address)
            if (!personalInfoViewModel.profileAvatarUrl.value.isNullOrEmpty()) {
                if (isValid) {
                    when (workFlow) {
                        WorkFlow.NEW_CREATION -> {
                            resumeId?.let { it1 ->
                                if (profileUrl != null) {
                                    personalInfoViewModel.savePersonalInformation(
                                        it1,
                                        fullName,
                                        email,
                                        mobile,
                                        address,
                                        profileUrl
                                    )
                                }
                            }
                        }

                        WorkFlow.UPDATE_EXISTING -> {
                            resumeId?.let { it1 ->
                                if (profileUrl != null) {
                                    personalInfoViewModel.updatePersonalInformation(
                                        it1,
                                        fullName,
                                        email,
                                        mobile,
                                        address,
                                        profileUrl
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Please add your profile picture", Toast.LENGTH_LONG).show()
            }
        }

        fragmentAddPersonalInformationBinding.profileImage.setOnClickListener {
            takePhoto()
        }
    }

    private fun initObserversForUpdateFlow() {
        personalInfoViewModel.personalInfoIdExisting.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    fragmentAddPersonalInformationBinding.edtFullName.setText(it.data?.fullName)
                    fragmentAddPersonalInformationBinding.edtEmail.setText(it.data?.email)
                    fragmentAddPersonalInformationBinding.edtMobile.setText(it.data?.phoneNumber)
                    fragmentAddPersonalInformationBinding.edtAddress.setText(it.data?.address)
                    fragmentAddPersonalInformationBinding.btnNextPersonInfo.text =
                        getString(R.string.update)
                    val profileAvatarUrl = it.data?.profilePicture
                    personalInfoViewModel.profileAvatarUrl.value = profileAvatarUrl
                    glideRequestManager.load(Uri.parse(profileAvatarUrl)).into(fragmentAddPersonalInformationBinding.profileImage)
                }

                is Resource.Error -> {
                    Toast.makeText(
                        context,
                        "Error Occurred obtaining personal information",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> Toast.makeText(context, "Error Occurred", Toast.LENGTH_LONG).show()
            }
        })

        personalInfoViewModel.personalInfoUpdate.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(context, "Personal Information updated successfully!", Toast.LENGTH_LONG).show()
                    resumeId?.let { it1 -> personalInfoViewModel.updateResumeStatus(resumeId = it1, ProgressStatus.COMPLETED_PERSONAL_INFO) }
                }

                is Resource.Error -> {
                    Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
                }
                else -> Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
            }
        })

        personalInfoViewModel.resumeStatusUpdate.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                }

                is Resource.Error -> {
                    Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
                }
                else -> Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun resetFormValues() {
        fragmentAddPersonalInformationBinding.btnNextPersonInfo.text =
            getString(R.string.next)
    }

    private fun initObserversForNewDataFlow() {
        personalInfoViewModel.personalInfoId.observe(viewLifecycleOwner, { data ->
            when (data) {
                is Resource.Success -> {
                    fragmentAddPersonalInformationBinding.btnNextPersonInfo.isEnabled = false
                    fragmentAddPersonalInformationBinding.btnNextPersonInfo.isClickable = false
                    fragmentAddPersonalInformationBinding.btnNextPersonInfo.text =
                        getText(R.string.update)
                    Toast.makeText(context, "Personal Information Saved successfully!", Toast.LENGTH_LONG).show()
                    resumeId?.let { it1 -> personalInfoViewModel.updateResumeStatus(resumeId = it1, ProgressStatus.COMPLETED_PERSONAL_INFO) }
                }

                is Resource.Error -> {
                    Toast.makeText(context, "Error occurred while creating new personal information", Toast.LENGTH_LONG).show()
                }
                else -> Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun validateFields(
        fullName: String,
        email: String,
        mobile: String,
        address: String,
    ): Boolean {
        if (fullName.isEmpty()) {
            fragmentAddPersonalInformationBinding.edtFullNameTp.helperText =
                getString(R.string.enter_fullname)

            return false
        } else {
            fragmentAddPersonalInformationBinding.edtFullNameTp.helperText = ""
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            fragmentAddPersonalInformationBinding.edtEmailTp.helperText =
                getString(R.string.enter_email)
            return false
        } else {
            fragmentAddPersonalInformationBinding.edtEmailTp.helperText = ""
        }

        if (mobile.isEmpty() || !mobile.matches(".*[0-9].*".toRegex())) {
            fragmentAddPersonalInformationBinding.edtMobileNoTp.helperText =
                getString(R.string.enter_mobile)
            return false
        } else {
            fragmentAddPersonalInformationBinding.edtMobileNoTp.helperText = ""
        }

        if (address.isEmpty()) {
            fragmentAddPersonalInformationBinding.edtAddressTp.helperText =
                getString(R.string.enter_address)
            return false
        } else {
            fragmentAddPersonalInformationBinding.edtAddressTp.helperText = ""
        }
        return true
    }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (granted) {
                launchGallery()
            }
        }

    private fun takePhoto() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            launchGallery()
        }
        activity?.let {
            if (hasPermissions(activity as Context, PERMISSIONS)) {
                launchGallery()
            } else {
                permReqLauncher.launch(
                    PERMISSIONS
                )
            }
        }
    }

    private fun onActivityResult(requestCode: Int, result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            when (requestCode) {
                Constants.MY_CODE_REQUEST -> {
                    if (intent != null) {
                        personalInfoViewModel.profileAvatarUrl.value = intent.data.toString()
                        glideRequestManager.load(Uri.parse(personalInfoViewModel.profileAvatarUrl.value.toString())).into(fragmentAddPersonalInformationBinding.profileImage)
                    }
                }
            }
        }
    }

    private fun registerGalleryLaunchActivityForResult() {
        galleryLaunchIntent = Intent()
        galleryLaunchIntent.type = "image/*"
        galleryLaunchIntent.action = Intent.ACTION_GET_CONTENT

        activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            onActivityResult(Constants.MY_CODE_REQUEST, result)
        }
    }
    private fun launchGallery() {
        activityLauncher.launch(galleryLaunchIntent)
    }
}
