package com.amyu.sample

import org.threeten.bp.LocalDate


data class Weight(
    val date: LocalDate,
    val weight: Float?
) {
    companion object {
        private val weights: List<Float?> = listOf(50.0f, 50.5f, 52.5f, 53.4f, 54.3f, 55.5f, 56.7f, 57.8f, 58.9f, 59.0f, 62.2f, null)
        fun createSampleList(): List<Weight> = (-5..500).map {
            Weight(
                weight = if (it > -1) {
                    weights.shuffled().first()
                } else {
                    null
                },
                date = LocalDate.now().minusDays(it.toLong())
            )
        }.sortedByDescending { it.date }
    }
}