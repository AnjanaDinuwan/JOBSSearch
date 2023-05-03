package com.example.jobssearch.data.model

data class Provider(
    val id:Int,
    var password: String,
    var email: String,
    val companyName:String,
    val userName:String,
    val logo:String,
    val contact:String,
    val description:String,
    val location: String,
)
