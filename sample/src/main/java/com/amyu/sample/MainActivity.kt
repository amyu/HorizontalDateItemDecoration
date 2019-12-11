package com.amyu.sample

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.PopupWindowCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amyu.horizontal_date_item_decoration.HorizontalDateItemDecoration
import com.amyu.sample.databinding.ActivityMainBinding
import com.amyu.sample.library.GraphAdapter
import com.amyu.sample.library.GraphItemViewModel
import com.amyu.sample.library.VerticalItemDecoration
import org.threeten.bp.LocalDate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setContentView(binding.root)

        val weights = Weight.createSampleList()
        val (maxWeight, minWeight) = weights.mapNotNull { it.weight }.let { it.max()!! + 5 to it.min()!! - 5 }
        val graphItemViewModelList = GraphItemViewModel.convert(weights, maxWeight, minWeight)

        val graphAdapter = GraphAdapter(this, ::onItemClick)

        binding.graphRecyclerView.apply {
            adapter = graphAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, true)
            addItemDecoration(object : HorizontalDateItemDecoration(context) {
                override fun getDate(position: Int): LocalDate {
                    return weights[position].date
                }
            })
            addItemDecoration(
                VerticalItemDecoration(
                    this@MainActivity,
                    listOf("a", "b", "c", "d", "e", "f", "g", "h", "i")
                )
            )
        }
        graphAdapter.submitList(graphItemViewModelList)
    }

    private fun onItemClick(
        dotView: View,
        weight: Float
    ) {
        val textView = TextView(applicationContext).apply {
            text = weight.toString()
        }
        val popupWindow = PopupWindow(applicationContext).apply {
            contentView = textView
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            isOutsideTouchable = true
        }
        PopupWindowCompat.showAsDropDown(popupWindow, dotView, 0, 0, Gravity.CENTER)
    }
}