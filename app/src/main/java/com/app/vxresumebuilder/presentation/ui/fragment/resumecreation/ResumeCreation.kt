package com.app.vxresumebuilder.presentation.ui.fragment.resumecreation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.app.vxresumebuilder.R
import com.app.vxresumebuilder.databinding.FragmentResumeCreationBinding
import com.app.vxresumebuilder.presentation.ui.adapter.ViewPagerAdapter
import com.app.vxresumebuilder.presentation.ui.fragment.personal.PersonalInformationArgs
import com.google.android.material.tabs.TabLayoutMediator

class ResumeCreation : Fragment(R.layout.fragment_resume_creation) {

    private lateinit var resumeCreationBinding: FragmentResumeCreationBinding

    private val args: PersonalInformationArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        resumeCreationBinding = FragmentResumeCreationBinding.bind(view)
        resumeCreationBinding.vpInformation.adapter = ViewPagerAdapter(this, args.flowFlag, args.resumeId)
        TabLayoutMediator(resumeCreationBinding.tbLayoutInformation, resumeCreationBinding.vpInformation) { tab, pos ->
            when (pos) {
                0 -> tab.text = getString(R.string.personal_info)
                1 -> tab.text = getString(R.string.education)
                2 -> tab.text = getString(R.string.experience)
                3 -> tab.text = getString(R.string.project_details)
            }
        }.attach()
    }
}
