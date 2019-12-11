package com.amyu.sample.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amyu.sample.databinding.ItemGraphBinding


class GraphViewHolder(private val binding: ItemGraphBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup
        ): GraphViewHolder = GraphViewHolder(ItemGraphBinding.inflate(inflater, parent, false))
    }

    fun bind(
        cellViewModel: GraphItemViewModel,
        onItemClick: (
            dotView: View,
            weight: Float
        ) -> Unit
    ) {
        binding.viewModel = cellViewModel
        binding.root.setOnClickListener {
            cellViewModel.weight?.let {
                onItemClick(binding.dotImageView, it)
            }
        }
        val layoutParams = binding.rootContainer.layoutParams
        layoutParams.width = cellViewModel.width.toInt()
        binding.rootContainer.layoutParams = layoutParams
    }
}