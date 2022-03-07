package com.app.vxresumebuilder.presentation.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.vxresumebuilder.R
import com.app.vxresumebuilder.data.dto.enumconst.ButtonType
import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus
import com.app.vxresumebuilder.data.dto.enumconst.WorkFlow
import com.app.vxresumebuilder.databinding.DialogResumeInformationBinding
import com.app.vxresumebuilder.databinding.FragmentHomeBinding
import com.app.vxresumebuilder.presentation.ui.adapter.rv.ResumeDataAdapter
import com.app.vxresumebuilder.presentation.viewmodel.ResumeCreationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Home : Fragment(R.layout.fragment_home) {

    private val resumeCreationViewModel: ResumeCreationViewModel by activityViewModels()
    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    @Inject
    lateinit var resumeDataAdapter: ResumeDataAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentHomeBinding = FragmentHomeBinding.bind(view)
        initialize()
        initObservers()
        initRecyclerView()
        initEditButton()
    }

    private fun initObservers() {

        resumeCreationViewModel.resumeDataList.observe(viewLifecycleOwner, { it ->
            it.data.let {
                resumeDataAdapter.submitList(it)
            }
        })

        resumeCreationViewModel.resumeRemoval.observe(viewLifecycleOwner, { it ->
            it.data.let {
                resumeCreationViewModel.getResumeDataList()
            }
        })
    }

    private fun initialize() {
        fragmentHomeBinding.addNewButton.setOnClickListener {
            displayResumeCreationDialog(it)
        }
        resumeCreationViewModel.getResumeDataList()
    }

    private fun initRecyclerView() {
        fragmentHomeBinding.rvResumeList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fragmentHomeBinding.rvResumeList.adapter = resumeDataAdapter
    }

    private fun initEditButton() {
        resumeDataAdapter.onItemClick = { context, resume, type ->
            when (type) {
                ButtonType.EDIT -> {
                    if (resume.status == ProgressStatus.PENDING) {
                        navigateToNext(WorkFlow.NEW_CREATION, resume.id)
                    } else {
                        navigateToNext(WorkFlow.UPDATE_EXISTING, resume.id)
                    }
                }
                ButtonType.REMOVE -> {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Are you sure you want to remove?")
                    builder.setPositiveButton(
                        "Yes"
                    ) { _, _ ->
                        run {
                            resumeCreationViewModel.removeResumeById(resume.id)
                        }
                    }
                    builder.setNegativeButton(
                        "No"
                    ) { dialog, _ -> dialog.cancel() }

                    builder.show()
                }

                ButtonType.ROOT -> {
                    navigateViewResume(resume.status, resume.id)
                }
            }
        }
    }

    private fun displayResumeCreationDialog(view: View) {
        val builder = AlertDialog.Builder(view.context)
        val binding: DialogResumeInformationBinding = DialogResumeInformationBinding.inflate(LayoutInflater.from(view.context))
        builder.setTitle("Enter Resume Title")
        builder.setPositiveButton(
            "Continue"
        ) { _, _ ->
            run {

                val resumeTitle = binding.edtResumeTitle.text.toString()
                val resumeDescription = binding.edtResumeDescription.text.toString()
                if (resumeTitle.isNotEmpty() && resumeDescription.isNotEmpty()) {
                    resumeCreationViewModel.createResume(resumeTitle, resumeDescription)
                    resumeCreationViewModel.resumeId.observe(viewLifecycleOwner, { it ->
                        it.data?.let { resumeId ->
                            navigateToNext(WorkFlow.NEW_CREATION, resumeId)
                        }
                    })
                } else {
                    Toast.makeText(
                        view.context,
                        getString(R.string.empty_validation_message_resume_title),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.cancel() }
        builder.setView(binding.root)
        builder.show()
    }

    private fun navigateToNext(workFlow: WorkFlow, resumeId: Long) {
        val action = HomeDirections.actionHomeToResumeCreation(workFlow, resumeId)
        findNavController().navigate(action)
    }

    private fun navigateViewResume(progressStatus: ProgressStatus, resumeId: Long) {
        if (progressStatus == ProgressStatus.CREATED) {
            val action = HomeDirections.actionHomeToResumeViewer()
            findNavController().navigate(action)
        }
    }
}
