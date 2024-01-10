package com.joaorodrigues.tasks.service.model

import com.google.gson.annotations.SerializedName

class PersonModel {

    @SerializedName("token")
    lateinit var token: String

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("personKey")
    lateinit var personKey: String
}