package com.example.jobssearch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jobssearch.data.model.Job
import com.example.jobssearch.data.model.Seeker

@Dao
interface SeekerDao {

    @Insert
    suspend fun insertSeeker(s: Seeker)

}