package com.uniandes.vynilsmobile.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.model.Collector
import com.uniandes.vynilsmobile.databinding.CollectorItemBinding

class CollectorsAdapter(private val progressBar: ProgressBar, private val onItemClick: (Collector) -> Unit) : RecyclerView.Adapter<CollectorsAdapter.CollectorViewHolder>(){

    private val asyncListDiffer = AsyncListDiffer(this, CollectorDiffCallback())

    var collectors: List<Collector>
        get() = asyncListDiffer.currentList
        set(value) {
            asyncListDiffer.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val withDataBinding: CollectorItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorViewHolder.LAYOUT,
            parent,
            false)
        return CollectorViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        val collector = asyncListDiffer.currentList[position]
        holder.bind(collector)

        holder.viewDataBinding.collector = collector
        holder.viewDataBinding.root.setOnClickListener {
            onItemClick(collector)
        }

        progressBar.visibility = if(itemCount > 0) View.GONE else View.VISIBLE

    }

    override fun getItemCount(): Int {
        return collectors.size
    }


    class CollectorViewHolder(val viewDataBinding: CollectorItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_item
        }

        fun bind(collector: Collector) {
            viewDataBinding.collector = collector

        }

    }
    private class CollectorDiffCallback : DiffUtil.ItemCallback<Collector>() {
        override fun areItemsTheSame(oldItem: Collector, newItem: Collector): Boolean {
            return oldItem.collectorId == newItem.collectorId
        }
        override fun areContentsTheSame(oldItem: Collector, newItem: Collector): Boolean {
            return oldItem == newItem
        }
    }
}