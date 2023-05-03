package com.example.jobssearch.data.model


data class Job(
    val id:Int,
    val companyID:Int,
    var companyName: String?,
    var companyLogo: String?,
    val time:String,
    val name:String,
    val description:String,
    val salary:String,

)
