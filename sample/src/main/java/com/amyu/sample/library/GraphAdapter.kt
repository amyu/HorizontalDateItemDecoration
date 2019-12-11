package com.amyu.sample.library

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


class GraphAdapter(
    context: Context,
    private val onItemClick: (dotView: View, weight: Float) -> Unit
) : ListAdapter<GraphItemViewModel, GraphViewHolder>(diffUtil) {
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<GraphItemViewModel>() {
            override fun areContentsTheSame(
                oldItem: GraphItemViewModel,
                newItem: GraphItemViewModel
            ): Boolean =
                false

            override fun areItemsTheSame(
                oldItem: GraphItemViewModel,
                newItem: GraphItemViewModel
            ): Boolean =
                false
        }
    }

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GraphViewHolder = GraphViewHolder.create(inflater, parent)

    override fun onBindViewHolder(holder: GraphViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

}