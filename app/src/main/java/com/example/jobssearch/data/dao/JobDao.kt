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

    @Query("SELECT jobs.id, jobs.name, jobs.description, providers.company_name, " +
            "providers.logo " +
            "FROM jobs, providers " +
            "WHERE jobs.company_id = providers.id")
    suspend fun getJobWithCompany(): List<MainDataSource.JobCompanyInfo>

    @Query("SELECT jobs.id, jobs.name, jobs.description, providers.company_name, " +
            "providers.logo " +
            "FROM jobs " +
            "INNER JOIN providers " +
            "ON jobs.company_id = providers.id " +
            "WHERE jobs.name LIKE '%' || :search || '%'" +
            "   OR jobs.description LIKE '%' || :search || '%'")
    suspend fun searchQuery(search: String): List<MainDataSource.JobCompanyInfo>

}