package com.app.vxresumebuilder.presentation.ui.fragment.project

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.vxresumebuilder.R
import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus
import com.app.vxresumebuilder.data.dto.enumconst.WorkFlow
import com.app.vxresumebuilder.databinding.FragmentAddProjectInformationBinding
import com.app.vxresumebuilder.presentation.ui.fragment.resumecreation.ResumeCreationDirections
import com.app.vxresumebuilder.presentation.viewmodel.projectinfo.ProjectInfoViewModel
import com.app.vxresumebuilder.util.Constants
import com.app.vxresumebuilder.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectInformation : Fragment(R.layout.fragment_add_project_information) {

    private lateinit var fragmentAddProjectInformationBinding: FragmentAddProjectInformationBinding
    private val projectInfoViewModel: ProjectInfoViewModel by viewModels()
    private var resumeId: Long? = 0
    private var informationDataId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentAddProjectInformationBinding =
            FragmentAddProjectInformationBinding.bind(view)
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
                informationDataId?.let { projectInfoViewModel.getExistingProjectInformation(it) }
            }
        }
    }

    private fun initObserversForUpdateFlow() {
        fragmentAddProjectInformationBinding.btnNextProjectInfo.text =
            getText(R.string.update)
        projectInfoViewModel.projectInformationDataExisting.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    fragmentAddProjectInformationBinding.edtProjectTitle.setText(it.data?.title)
                    fragmentAddProjectInformationBinding.edtClientName.setText(it.data?.clientName)
                    fragmentAddProjectInformationBinding.edtDuration.setText(it.data?.duration)
                    fragmentAddProjectInformationBinding.edtDescription.setText(it.data?.description)
                    fragmentAddProjectInformationBinding.edtRole.setText(it.data?.position)
                }

                is Resource.Error -> {
                    Toast.makeText(
                        context,
                        "Error Occurred obtaining project information",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> Toast.makeText(context, "Error Occurred", Toast.LENGTH_LONG).show()
            }
        })

        projectInfoViewModel.projectInformationUpdate.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(context, "Project Info updated successfully!", Toast.LENGTH_LONG).show()
                    resumeId?.let { it1 -> projectInfoViewModel.updateResumeStatus(resumeId = it1, ProgressStatus.COMPLETED_PROJECT_INFO) }
                }

                is Resource.Error -> {
                    Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
                }
                else -> Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initDataObserversForEducationData() {
        projectInfoViewModel.projectInformationDataNew.observe(viewLifecycleOwner, { data ->
            when (data) {
                is Resource.Success -> {
                    fragmentAddProjectInformationBinding.btnNextProjectInfo.isEnabled = false
                    fragmentAddProjectInformationBinding.btnNextProjectInfo.isClickable =
                        false
                    fragmentAddProjectInformationBinding.btnNextProjectInfo.text =
                        getText(R.string.update)
                    Toast.makeText(
                        context,
                        "Project Information Saved successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                    resumeId?.let { it1 ->
                        projectInfoViewModel.updateResumeStatus(
                            resumeId = it1,
                            ProgressStatus.COMPLETED_PROJECT_INFO
                        )
                    }
                }

                is Resource.Error -> {
                    Toast.makeText(
                        context,
                        "Error occurred while creating new project information",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
            }
        })
        registerResumeStatusUpdateObserver()
    }

    private fun registerResumeStatusUpdateObserver() {
        projectInfoViewModel.resumeStatusUpdate.observe(viewLifecycleOwner, {
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
        fragmentAddProjectInformationBinding.fabNavigateToPdfViewer.setOnClickListener {
            val action = ProjectInformationDirections.actionProjectInformationToResumeViewer(
                resumeId!!
            )
            findNavController().navigate(action)
        }

        fragmentAddProjectInformationBinding.btnNextProjectInfo.setOnClickListener {
            val projectTitle = fragmentAddProjectInformationBinding.edtProjectTitle.text.toString()
            val clientName =
                fragmentAddProjectInformationBinding.edtClientName.text.toString()
            val duration = fragmentAddProjectInformationBinding.edtDuration.text.toString()
            val description = fragmentAddProjectInformationBinding.edtDescription.text.toString()
            val role = fragmentAddProjectInformationBinding.edtRole.text.toString()
            if (validateFields(projectTitle, clientName, duration, description, role)) {

                if (insertFlow) {
                    resumeId?.let { it1 ->
                        projectInfoViewModel.saveProjectInformation(
                            it1,
                            projectTitle,
                            clientName,
                            duration,
                            description,
                            role,
                            false,
                        )
                    }
                } else {
                    projectInfoViewModel.updateProjectInformation(
                        informationDataId,
                        projectTitle,
                        clientName,
                        duration,
                        description,
                        role,
                        false
                    )
                }
            }
        }
    }

    private fun validateFields(
        projectTitle: String,
        clientName: String,
        duration: String,
        description: String,
        role: String,
    ): Boolean {
        if (projectTitle.isEmpty()) {
            fragmentAddProjectInformationBinding.edtPProjectName.helperText =
                getString(R.string.enter_project_title)
            return false
        } else {
            fragmentAddProjectInformationBinding.edtPProjectName.helperText = ""
        }

        if (clientName.isEmpty()) {
            fragmentAddProjectInformationBinding.edtPProjectClient.helperText =
                getString(R.string.enter_client_information)
            return false
        } else {
            fragmentAddProjectInformationBinding.edtPProjectClient.helperText = ""
        }

        if (duration.isEmpty()) {
            fragmentAddProjectInformationBinding.edtRDuration.helperText =
                getString(R.string.enter_duration)
            return false
        } else {
            fragmentAddProjectInformationBinding.edtRDuration.helperText = ""
        }

        if (description.isEmpty()) {
            fragmentAddProjectInformationBinding.edtRDescription.helperText =
                getString(R.string.enter_projct_desciption)
            return false
        } else {
            fragmentAddProjectInformationBinding.edtRDescription.helperText = ""
        }

        if (role.isEmpty()) {
            fragmentAddProjectInformationBinding.edtRRole.helperText =
                getString(R.string.enter_project_role)
            return false
        } else {
            fragmentAddProjectInformationBinding.edtRRole.helperText = ""
        }

        return true
    }
}
