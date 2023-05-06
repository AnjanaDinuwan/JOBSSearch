package com.example.jobssearch.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "jobs")
data class Job(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name="company_id") val companyID:Int,
    val time:String,
    val name:String,
    val description:String,
    val salary:String,
)
