package com.mentormate.andriivanov.retrofit101.data

import javax.inject.Singleton


@Singleton
class BestChampion private constructor() {

    var name: String? = ""
    private var bestChampId: Int = 0
    private var maxPoints: Int = 0

    fun getBestChampId(): Int? {
        return bestChampId
    }

    fun setBestChampId(bestChampId: Int?) {
        this.bestChampId = bestChampId!!
    }

    fun getMaxPoints(): Int? {
        return maxPoints
    }

    fun setMaxPoints(maxPoints: Int?) {
        this.maxPoints = maxPoints!!
    }

    companion object {

        val instance = BestChampion()
    }
}
