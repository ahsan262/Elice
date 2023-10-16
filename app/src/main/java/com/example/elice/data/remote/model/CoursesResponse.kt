package com.example.elice.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class CoursesResponse(
    @SerializedName("_result") var Result: Result? = Result(),
    @SerializedName("courses") var cours: ArrayList<Course> = arrayListOf(),
    @SerializedName("course_count") var courseCount: Int? = null
) : Serializable

