package com.example.jobssearch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jobssearch.data.MainDataSource
import com.example.jobssearch.data.model.Job

@Dao
interface JobDao {
    @Query("SELECT * FROM jobs")
    suspend fun getAll(): List<Job>

    @Insert
    suspend fun insertJob(j: Job)

    @Query("SELECT jobs.id, jobs.name, jobs.description, providers.company_name " +
            "FROM jobs, providers " +
            "WHERE jobs.company_id = providers.id")
    suspend fun getJobWithCompany(): List<MainDataSource.JobCompanyInfo>

}