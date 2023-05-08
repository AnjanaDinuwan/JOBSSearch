package com.example.jobssearch.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.Inet4Address

@Entity(tableName = "services")
data class Service(
    @PrimaryKey(autoGenerate = true) val id:Int,
    var name: String,
    var email: String,
    val address:String,
    val rate:String,
    val skills:String,
    val logo:String,

)