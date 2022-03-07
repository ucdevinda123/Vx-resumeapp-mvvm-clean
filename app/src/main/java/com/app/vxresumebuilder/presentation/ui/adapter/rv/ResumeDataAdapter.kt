package com.app.vxresumebuilder.presentation.ui.adapter.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.vxresumebuilder.data.dto.entity.Resume
import com.app.vxresumebuilder.data.dto.enumconst.ButtonType
import com.app.vxresumebuilder.data.dto.enumconst.ProgressStatus
import com.app.vxresumebuilder.databinding.ItemHomeResumeListBinding
import com.kofigyan.stateprogressbar.StateProgressBar
import javax.inject.Inject

class ResumeDataAdapter @Inject constructor() :
    ListAdapter<Resume, ResumeDataAdapter.ResumeDataAdapterViewHolder>(
        ResumeItemDiffUtil()
    ) {

    var onItemClick: ((Context, Resume, ButtonType) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ResumeDataAdapterViewHolder {
        var binding =
            ItemHomeResumeListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ResumeDataAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResumeDataAdapterViewHolder, position: Int) {
        val itemConvertedResult = getItem(position)
        if (itemConvertedResult != null) {
            holder.bind(itemConvertedResult)
        }
    }

    inner class ResumeDataAdapterViewHolder(private val itemHomeResumeListBinding: ItemHomeResumeListBinding) :
        RecyclerView.ViewHolder(itemHomeResumeListBinding.root) {

        fun bind(resume: Resume) {
            itemHomeResumeListBinding.apply {
                itemHomeResumeListBinding.resumeTitle.text = resume.name
                itemHomeResumeListBinding.resumeStatus.text = resume.status.nameStatus
                itemHomeResumeListBinding.resumeDescription.text = resume.descriptionOfYou
                setStatusProgressNumber(resume.status.stateNum)

                itemHomeResumeListBinding.editResumeBtn.setOnClickListener {
                    onItemClick?.invoke(this.root.context, resume, ButtonType.EDIT)
                }

                itemHomeResumeListBinding.removeBtn.setOnClickListener {
                    onItemClick?.invoke(this.root.context, resume, ButtonType.REMOVE)
                }

                itemHomeResumeListBinding.root.setOnClickListener {
                    onItemClick?.invoke(this.root.context, resume, ButtonType.ROOT)
                }
            }
        }

        private fun setStatusProgressNumber(stateNum: Int) {
     when(stateNum){
         1 -> itemHomeResumeListBinding.resumeStatusBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)
         2 -> itemHomeResumeListBinding.resumeStatusBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO)
         3 -> itemHomeResumeListBinding.resumeStatusBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE)
         4 -> itemHomeResumeListBinding.resumeStatusBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR)
     }
        }
    }

    class ResumeItemDiffUtil : DiffUtil.ItemCallback<Resume>() {
        override fun areItemsTheSame(oldItem: Resume, newItem: Resume): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Resume, newItem: Resume): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
}
