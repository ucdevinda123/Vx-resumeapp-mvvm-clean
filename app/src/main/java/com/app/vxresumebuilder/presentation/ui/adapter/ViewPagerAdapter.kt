package com.app.vxresumebuilder.presentation.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.vxresumebuilder.data.dto.enumconst.WorkFlow
import com.app.vxresumebuilder.presentation.ui.fragment.informationdata.InformationDataList
import com.app.vxresumebuilder.presentation.ui.fragment.personal.PersonalInformation

class ViewPagerAdapter(val fragment: Fragment, private val workFlowId: WorkFlow, val resumeId: Long) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PersonalInformation.newInstance(workFlowId, resumeId)
            1, 2, 3 -> InformationDataList().newInstance(resumeId, position)
            else -> InformationDataList().newInstance(resumeId, position)
        }
    }
}
