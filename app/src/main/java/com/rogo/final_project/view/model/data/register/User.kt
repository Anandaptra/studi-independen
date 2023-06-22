package com.rogo.final_project.view.model.data.register


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone Number")
    val phoneNumber: String
)