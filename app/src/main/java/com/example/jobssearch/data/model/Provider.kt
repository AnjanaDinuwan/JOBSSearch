package com.example.jobssearch.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "providers")
data class Provider(
    @PrimaryKey(autoGenerate = true) val id:Int,
    var password: String,
    var email: String,
    @ColumnInfo(name = "company_name") val companyName:String,
    @ColumnInfo(name = "user_name") val userName:String,
    val logo:String,
    val contact:String,
    val description:String,
    val location: String,
)
