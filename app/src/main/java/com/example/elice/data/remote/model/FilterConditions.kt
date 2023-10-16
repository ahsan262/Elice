package com.example.elice.data.remote.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FilterConditions(@SerializedName("course_ids") var courseIds: List<Int> = listOf())
