package com.app.vxresumebuilder.presentation.ui.fragment.education

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.vxresumebuilder.R
import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus
import com.app.vxresumebuilder.data.dto.enumconst.WorkFlow
import com.app.vxresumebuilder.databinding.FragmentAddEducationalInformationBinding
import com.app.vxresumebuilder.presentation.viewmodel.educationalinfo.EducationalInfoViewModel
import com.app.vxresumebuilder.util.Constants
import com.app.vxresumebuilder.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EducationalInformation : Fragment(R.layout.fragment_add_educational_information) {

    companion object {

        fun newInstance(
            flow: WorkFlow,
            resumeId: Long,
            informationDataId: Int,
        ): EducationalInformation {
            return EducationalInformation().apply {
                arguments = Bundle().apply {
                    putSerializable(Constants.DATA_FLOW_FLAG, flow)
                    putLong(Constants.DATA_FLOW_RESUME_ID, resumeId)
                    putInt(Constants.DATA_FLOW_INFORMATION_DATA_ID, informationDataId)
                }
            }
        }
    }

    private lateinit var fragmentAddEducationalInformationBinding: FragmentAddEducationalInformationBinding
    private val educationalInfoViewModel: EducationalInfoViewModel by viewModels()
    private var resumeId: Long? = 0
    private var informationDataId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentAddEducationalInformationBinding =
            FragmentAddEducationalInformationBinding.bind(view)
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
                informationDataId?.let { educationalInfoViewModel.getExistingEducationInformation(it) }
            }
        }
    }

    private fun initObserversForUpdateFlow() {
        fragmentAddEducationalInformationBinding.btnNextEducationInfo.text =
            getText(R.string.update)
        educationalInfoViewModel.educationDataExisting.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    fragmentAddEducationalInformationBinding.edtSchool.setText(it.data?.title)
                    fragmentAddEducationalInformationBinding.completionYear.setText(it.data?.description)
                    fragmentAddEducationalInformationBinding.gpa.setText(it.data?.gpa)
                    fragmentAddEducationalInformationBinding.grade.setText(it.data?.grade)
                }

                is Resource.Error -> {
                    Toast.makeText(
                        context,
                        "Error Occurred obtaining educational information",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> Toast.makeText(context, "Error Occurred", Toast.LENGTH_LONG).show()
            }
        })

        educationalInfoViewModel.eduInfoUpdate.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(context, "Educational Info updated successfully!", Toast.LENGTH_LONG).show()
                    resumeId?.let { it1 -> educationalInfoViewModel.updateResumeStatus(resumeId = it1, ProgressStatus.COMPLETED_EDUCATION_INFO) }
                }

                is Resource.Error -> {
                    Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
                }
                else -> Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initDataObserversForEducationData() {
        educationalInfoViewModel.educationDataNew.observe(viewLifecycleOwner, { data ->
            when (data) {
                is Resource.Success -> {
                    fragmentAddEducationalInformationBinding.btnNextEducationInfo.isEnabled = false
                    fragmentAddEducationalInformationBinding.btnNextEducationInfo.isClickable =
                        false
                    fragmentAddEducationalInformationBinding.btnNextEducationInfo.text =
                        getText(R.string.update)
                    Toast.makeText(
                        context,
                        "Education Information Saved successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                    resumeId?.let { it1 ->
                        educationalInfoViewModel.updateResumeStatus(
                            resumeId = it1,
                            ProgressStatus.COMPLETED_EDUCATION_INFO
                        )
                    }
                }

                is Resource.Error -> {
                    Toast.makeText(
                        context,
                        "Error occurred while creating new educational information",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
            }
        })
        registerResumeStatusUpdateObserver()
    }

    private fun registerResumeStatusUpdateObserver() {
        educationalInfoViewModel.resumeStatusUpdate.observe(viewLifecycleOwner, {
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
        fragmentAddEducationalInformationBinding.btnNextEducationInfo.setOnClickListener {
            val educationSchool = fragmentAddEducationalInformationBinding.edtSchool.text.toString()
            val completionYear =
                fragmentAddEducationalInformationBinding.completionYear.text.toString()
            val gpa = fragmentAddEducationalInformationBinding.gpa.text.toString()
            val grade = fragmentAddEducationalInformationBinding.grade.text.toString()

            if (validateFields(educationSchool, completionYear)) {

                if (insertFlow) {
                    resumeId?.let { it1 ->
                        educationalInfoViewModel.saveEducationInformation(
                            it1,
                            educationSchool,
                            completionYear,
                            gpa,
                            grade
                        )
                    }
                } else {
                    educationalInfoViewModel.updateEducationInformation(
                        informationDataId,
                        educationSchool,
                        completionYear,
                        gpa,
                        grade
                    )
                }
            }
        }
    }

    private fun validateFields(
        school: String,
        completionYear: String,
    ): Boolean {
        if (school.isEmpty()) {
            fragmentAddEducationalInformationBinding.edtFSchool.helperText =
                getString(R.string.enter_school_name)
            return false
        } else {
            fragmentAddEducationalInformationBinding.edtFSchool.helperText = ""
        }

        if (completionYear.isEmpty()) {
            fragmentAddEducationalInformationBinding.edtFCompletionYear.helperText =
                getString(R.string.enter_school_name)
            return false
        } else {
            fragmentAddEducationalInformationBinding.edtFCompletionYear.helperText = ""
        }

        return true
    }
}



