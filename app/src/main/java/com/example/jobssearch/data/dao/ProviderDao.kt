package com.example.jobssearch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jobssearch.data.model.Job
import com.example.jobssearch.data.model.Provider

@Dao
interface ProviderDao {
    @Query("SELECT * FROM providers")
    suspend fun getAll(): List<Provider>

    @Insert
    suspend fun insertProvider(p: Provider)

    @Query("SELECT * FROM providers WHERE id = :id")
    suspend fun getProvider(id: Int): Provider

    @Query("SELECT * FROM jobs WHERE company_id = :id")
    suspend fun getAllJobs(id: Int): List<Job>
}