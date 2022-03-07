package com.app.vxresumebuilder.presentation.ui.adapter.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.vxresumebuilder.data.dto.entity.InformationData
import com.app.vxresumebuilder.data.dto.enumconst.ButtonType
import com.app.vxresumebuilder.databinding.ItemInformationDataListBinding
import javax.inject.Inject

class InformationDataAdapter @Inject constructor() :
    ListAdapter<InformationData, InformationDataAdapter.InformationDataAdapterViewHolder>(
        InformationDataDiffUtil()
    ) {

    var onItemClick: ((Context, InformationData, ButtonType) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): InformationDataAdapterViewHolder {
        var binding =
            ItemInformationDataListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return InformationDataAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InformationDataAdapterViewHolder, position: Int) {
        val itemConvertedResult = getItem(position)
        if (itemConvertedResult != null) {
            holder.bind(itemConvertedResult)
        }
    }

    inner class InformationDataAdapterViewHolder(private val itemInformationDataListBinding: ItemInformationDataListBinding) :
        RecyclerView.ViewHolder(itemInformationDataListBinding.root) {

        fun bind(informationData: InformationData) {
            itemInformationDataListBinding.apply {
                itemInformationDataListBinding.tvInformationDataTitle.text = informationData.title
                itemInformationDataListBinding.tvInformationDataSubTitle.text =
                    informationData.category.name
                itemInformationDataListBinding.tvInformationDataSubTitleTwo.text =
                    informationData.from

                itemInformationDataListBinding.btnEditInformationData.setOnClickListener {
                    onItemClick?.invoke(this.root.context, informationData, ButtonType.EDIT)
                }

                itemInformationDataListBinding.btnRemoveInformationData.setOnClickListener {
                    onItemClick?.invoke(this.root.context, informationData, ButtonType.REMOVE)
                }
            }
        }
    }

    class InformationDataDiffUtil : DiffUtil.ItemCallback<InformationData>() {
        override fun areItemsTheSame(
            oldItem: InformationData,
            newItem: InformationData,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: InformationData,
            newItem: InformationData,
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
}