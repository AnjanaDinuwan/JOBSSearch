package com.example.jobssearch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jobssearch.data.model.Job
import com.example.jobssearch.data.model.Seeker

@Dao
interface SeekerDao {
    @Insert
    suspend fun insertSeeker(s: Seeker)

    @Query("SELECT * FROM seekers " +
            "WHERE username=:username AND password=:password " +
            "LIMIT 1")
    suspend fun authenticateSeeker(username: String, password: String) : Seeker

    @Update
    fun updateSeeker(seeker: Seeker)

}