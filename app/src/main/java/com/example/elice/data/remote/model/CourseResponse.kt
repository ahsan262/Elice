package com.example.elice.data.remote.model

import com.google.gson.annotations.SerializedName

data class CourseResponse(
    @SerializedName("_result") var Result: Result? = Result(),
    @SerializedName("course") var course: Course? = null
)
