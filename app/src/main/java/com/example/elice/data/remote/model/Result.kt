package com.example.elice.data.remote.model

import com.google.gson.annotations.SerializedName


data class Result(

    @SerializedName("status") var status: String? = null,
    @SerializedName("reason") var reason: String? = null
)