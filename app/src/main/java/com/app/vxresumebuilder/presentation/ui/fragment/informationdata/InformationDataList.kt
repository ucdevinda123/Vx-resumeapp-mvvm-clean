package com.app.vxresumebuilder.presentation.ui.fragment.informationdata

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.vxresumebuilder.R
import com.app.vxresumebuilder.data.dto.enumconst.ButtonType
import com.app.vxresumebuilder.data.dto.enumconst.Category
import com.app.vxresumebuilder.data.dto.enumconst.WorkFlow
import com.app.vxresumebuilder.databinding.FragmentInformationListBinding
import com.app.vxresumebuilder.presentation.ui.adapter.rv.InformationDataAdapter
import com.app.vxresumebuilder.presentation.ui.fragment.resumecreation.ResumeCreationDirections
import com.app.vxresumebuilder.presentation.viewmodel.informationdata.InformationDataListViewModel
import com.app.vxresumebuilder.util.Constants
import com.app.vxresumebuilder.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InformationDataList : Fragment(R.layout.fragment_information_list) {

    private val informationDataListViewModel: InformationDataListViewModel by viewModels()
    private lateinit var fragmentInformationListBinding: FragmentInformationListBinding
    private var resumeId: Long = 0
    private var screenId: Int = 0

    @Inject
    lateinit var informationDataAdapter: InformationDataAdapter

    fun newInstance(resumeId: Long, screenOption: Int): InformationDataList {
        return InformationDataList().apply {
            arguments = Bundle().apply {
                putLong(Constants.DATA_FLOW_RESUME_ID, resumeId)
                putInt(Constants.SCREEN_OPTION_INFORMATION_DATA, screenOption)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentInformationListBinding = FragmentInformationListBinding.bind(view)
        initialize()
        initObservers()
        initRecyclerView()
        initRvButtonAction()
    }

    private fun initialize() {
        resumeId = arguments?.getLong(Constants.DATA_FLOW_RESUME_ID)!!
        screenId = arguments?.getInt(Constants.SCREEN_OPTION_INFORMATION_DATA)!!
        setScreenTitle(screenId)
        fragmentInformationListBinding.addNewButton.setOnClickListener {
            navigateToInformationDataDetail(resumeId, screenId, WorkFlow.NEW_CREATION)
        }
    }

    private fun navigateToInformationDataDetail(resumeId: Long, screenId: Int, workFlow: WorkFlow, informationDataId: Int = 0) {
        when (screenId) {
            1 -> {
                val action = ResumeCreationDirections.actionResumeCreationToEducationalInformation(
                    screenId,
                    resumeId, workFlow, informationDataId
                )
                findNavController().navigate(action)
            }

            2 -> {
                val action = ResumeCreationDirections.actionResumeCreationToWorkExperience(
                    screenId,
                    resumeId, workFlow, informationDataId
                )
                findNavController().navigate(action)
            }

            3 -> {
                val action = ResumeCreationDirections.actionResumeCreationToProjectInformation(
                    screenId,
                    resumeId, workFlow, informationDataId
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun setScreenTitle(screenId: Int) {
        when (screenId) {
            1 -> {
                fragmentInformationListBinding.tvTitleCategory.text =
                    Category.EDUCATION.categoryName
                informationDataListViewModel.getInformationDataList(resumeId, Category.EDUCATION)
            }
            2 -> {
                fragmentInformationListBinding.tvTitleCategory.text =
                    Category.EXPERIENCE.categoryName
                informationDataListViewModel.getInformationDataList(resumeId, Category.EXPERIENCE)
            }
            3 -> {
                fragmentInformationListBinding.tvTitleCategory.text =
                    Category.PROJECT_DETAILS.categoryName
                informationDataListViewModel.getInformationDataList(
                    resumeId,
                    Category.PROJECT_DETAILS
                )
            }
        }
    }

    private fun initRecyclerView() {
        fragmentInformationListBinding.rvInformationList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fragmentInformationListBinding.rvInformationList.adapter = informationDataAdapter

        informationDataListViewModel.informationDataRemoval.observe(viewLifecycleOwner, { it ->
            setScreenTitle(screenId)
        })
    }

    private fun initObservers() {

        informationDataListViewModel.informationDataList.observe(viewLifecycleOwner, { it ->
            when (it) {
                is Resource.Success -> {
                    informationDataAdapter.submitList(it.data)
                }

                is Resource.Error -> {
                    Toast.makeText(
                        context,
                        "Error Occurred obtaining information data",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> informationDataAdapter.submitList(emptyList())
            }
        })
    }

    private fun initRvButtonAction() {
        informationDataAdapter.onItemClick = { context, informationData, type ->
            when (type) {
                ButtonType.EDIT -> {
                    navigateToInformationDataDetail(resumeId, screenId = screenId, WorkFlow.UPDATE_EXISTING, informationData.id)
                }
                ButtonType.REMOVE -> {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Are you sure you want to remove?")
                    builder.setPositiveButton(
                        "Yes"
                    ) { _, _ ->
                        run {
                            informationDataListViewModel.removeInformationDataById(informationData.id)
                        }
                    }
                    builder.setNegativeButton(
                        "No"
                    ) { dialog, _ -> dialog.cancel() }

                    builder.show()
                }
            }
        }
    }
}
