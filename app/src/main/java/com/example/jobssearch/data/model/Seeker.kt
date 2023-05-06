package com.example.jobssearch.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seekers")
data class Seeker(
    @PrimaryKey(autoGenerate = true) val id:Int,
    var name: String,
    var username: String,
    var password: String,
    var email: String,
    var cv:String,
    @ColumnInfo(name = "profile_photo") var profilePhoto:String,
)
