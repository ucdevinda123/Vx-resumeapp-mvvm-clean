package com.app.vxresumebuilder.presentation.ui.fragment.workexperience

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.vxresumebuilder.R
import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus
import com.app.vxresumebuilder.data.dto.enumconst.WorkFlow
import com.app.vxresumebuilder.databinding.FragmentAddWorkExperienceInformationBinding
import com.app.vxresumebuilder.presentation.viewmodel.workinfo.WorkInfoViewModel
import com.app.vxresumebuilder.util.Constants
import com.app.vxresumebuilder.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkExperience : Fragment(R.layout.fragment_add_work_experience_information) {

    private lateinit var fragmentAddWorkExperienceInformationBinding: FragmentAddWorkExperienceInformationBinding
    private val workInfoViewModel: WorkInfoViewModel by viewModels()
    private var resumeId: Long? = 0
    private var informationDataId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentAddWorkExperienceInformationBinding =
            FragmentAddWorkExperienceInformationBinding.bind(view)
        initDataCalls()
        initDataObserversForEducationData()
    }

    private fun initDataCalls() {
        val workFlow = arguments?.getSerializable(Constants.DATA_FLOW_FLAG)
        resumeId = arguments?.getLong(Constants.DATA_FLOW_RESUME_ID)
        informationDataId = arguments?.getInt(Constants.DATA_FLOW_INFORMATION_DATA_ID)!!

        when (workFlow) {
            WorkFlow.NEW_CREATION -> {
                initDataObserversForEducationData()
                initClickListeners(true)
            }
            else -> {
                initClickListeners(false)
                initObserversForUpdateFlow()
                informationDataId?.let { workInfoViewModel.getExistingWorkInformation(it) }
            }
        }
    }

    private fun initObserversForUpdateFlow() {
        fragmentAddWorkExperienceInformationBinding.btnNextExperienceInfo.text =
            getText(R.string.update)
        workInfoViewModel.workInfoDataExisting.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    fragmentAddWorkExperienceInformationBinding.edtCompanyName.setText(it.data?.title)
                    fragmentAddWorkExperienceInformationBinding.edtPosition.setText(it.data?.position)
                    fragmentAddWorkExperienceInformationBinding.edtDuration.setText(it.data?.duration)
                    fragmentAddWorkExperienceInformationBinding.edtDescription.setText(it.data?.description)
                }

                is Resource.Error -> {
                    Toast.makeText(
                        context,
                        "Error Occurred obtaining work information",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> Toast.makeText(context, "Error Occurred", Toast.LENGTH_LONG).show()
            }
        })

        workInfoViewModel.workInfoUpdate.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(context, "Work Info updated successfully!", Toast.LENGTH_LONG).show()
                    resumeId?.let { it1 -> workInfoViewModel.updateResumeStatus(resumeId = it1, ProgressStatus.COMPLETED_PROJECT_INFO) }
                }

                is Resource.Error -> {
                    Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
                }
                else -> Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initDataObserversForEducationData() {
        workInfoViewModel.workInforDataNew.observe(viewLifecycleOwner, { data ->
            when (data) {
                is Resource.Success -> {
                    fragmentAddWorkExperienceInformationBinding.btnNextExperienceInfo.isEnabled = false
                    fragmentAddWorkExperienceInformationBinding.btnNextExperienceInfo.isClickable =
                        false
                    fragmentAddWorkExperienceInformationBinding.btnNextExperienceInfo.text =
                        getText(R.string.update)
                    Toast.makeText(
                        context,
                        "Work Information Saved successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                    resumeId?.let { it1 ->
                        workInfoViewModel.updateResumeStatus(
                            resumeId = it1,
                            ProgressStatus.COMPLETED_PROJECT_INFO
                        )
                    }
                }

                is Resource.Error -> {
                    Toast.makeText(
                        context,
                        "Error occurred while creating new work information",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
            }
        })
        registerResumeStatusUpdateObserver()
    }

    private fun registerResumeStatusUpdateObserver() {
        workInfoViewModel.resumeStatusUpdate.observe(viewLifecycleOwner, {
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

    private fun initClickListeners(insertFlow: Boolean) {
        fragmentAddWorkExperienceInformationBinding.btnNextExperienceInfo.setOnClickListener {
            val companyName = fragmentAddWorkExperienceInformationBinding.edtCompanyName.text.toString()
            val position =
                fragmentAddWorkExperienceInformationBinding.edtPosition.text.toString()
            val duration = fragmentAddWorkExperienceInformationBinding.edtDuration.text.toString()
            val description = fragmentAddWorkExperienceInformationBinding.edtDescription.text.toString()
            if (validateFields(companyName, position, duration, description)) {

                if (insertFlow) {
                    resumeId?.let { it1 ->
                        workInfoViewModel.saveWorkInformation(
                            it1,
                            companyName,
                            position,
                            duration,
                            description,
                            false,
                        )
                    }
                } else {
                    workInfoViewModel.updateWorkInformation(
                        informationDataId,
                        companyName,
                        position,
                        duration,
                        description,
                        false
                    )
                }
            }
        }
    }

    private fun validateFields(
        companyName: String,
        position: String,
        duration: String,
        description: String,
    ): Boolean {
        if (companyName.isEmpty()) {
            fragmentAddWorkExperienceInformationBinding.edtRCompanyName.helperText =
                getString(R.string.enter_company_name)
            return false
        } else {
            fragmentAddWorkExperienceInformationBinding.edtRCompanyName.helperText = ""
        }

        if (position.isEmpty()) {
            fragmentAddWorkExperienceInformationBinding.edtRPosition.helperText =
                getString(R.string.enter_position_information)
            return false
        } else {
            fragmentAddWorkExperienceInformationBinding.edtRPosition.helperText = ""
        }

        if (duration.isEmpty()) {
            fragmentAddWorkExperienceInformationBinding.edtRDuration.helperText =
                getString(R.string.enter_duration_work)
            return false
        } else {
            fragmentAddWorkExperienceInformationBinding.edtRDuration.helperText = ""
        }

        if (description.isEmpty()) {
            fragmentAddWorkExperienceInformationBinding.edtRDescription.helperText =
                getString(R.string.enter_desciption_work)
            return false
        } else {
            fragmentAddWorkExperienceInformationBinding.edtRDescription.helperText = ""
        }
        return true
    }
}
