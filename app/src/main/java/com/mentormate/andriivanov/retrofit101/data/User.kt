package com.mentormate.andriivanov.retrofit101.data

import javax.inject.Inject

data class User @Inject constructor(
        var name: String,
        val hero: String
)