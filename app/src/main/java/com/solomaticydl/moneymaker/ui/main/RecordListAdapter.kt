package com.solomaticydl.moneymaker.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.solomaticydl.moneymaker.databinding.ItemRecordBinding
import com.solomaticydl.moneymaker.db.entity.Record
import com.solomaticydl.moneymaker.db.entity.text
import com.solomaticydl.moneymaker.utils.MoneyUtil
import com.solomaticydl.moneymaker.utils.TimeUtil

class RecordListAdapter : ListAdapter<Record, RecordListAdapter.ViewHolder>(DiffCallback()) {

    internal var clickListener: (Record) -> Unit = { _ -> }

    internal var longClickListener: (Record) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickListener, longClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        itemRecordBinding: ItemRecordBinding,
        private val clickListener: (Record) -> Unit,
        private val longClickListener: (Record) -> Unit
    ) : RecyclerView.ViewHolder(itemRecordBinding.root) {

        private val primaryIdTv: TextView = itemRecordBinding.tvPrimaryId
        private val dateTv: TextView = itemRecordBinding.tvDate
        private val categoryTv: TextView = itemRecordBinding.tvCategory
        private val descriptionTv: TextView = itemRecordBinding.tvDescription
        private val moneyTv: TextView = itemRecordBinding.tvMoney

        fun bind(record: Record) {
            itemView.setOnClickListener {
                clickListener.invoke(record)
            }
            itemView.setOnLongClickListener {
                longClickListener.invoke(record)
                false
            }
            primaryIdTv.text = "${record.id}"
            dateTv.text = TimeUtil.timestampToDateString(record.dateTimestamp)
            categoryTv.text = record.category.text()
            if (record.description.isNotEmpty()) {
                descriptionTv.text = record.description
                descriptionTv.visibility = View.VISIBLE
            } else {
                descriptionTv.visibility = View.GONE
            }
            moneyTv.text = MoneyUtil.moneyToString(itemView.context, record.money)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Record>() {

        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem == newItem
        }
    }
}