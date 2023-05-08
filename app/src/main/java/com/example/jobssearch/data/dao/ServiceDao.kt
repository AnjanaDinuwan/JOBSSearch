package com.example.jobssearch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jobssearch.data.model.Job
import com.example.jobssearch.data.model.Provider
import com.example.jobssearch.data.model.Seeker
import com.example.jobssearch.data.model.Service

@Dao
interface ServiceDao {

    @Insert
    suspend fun insertService(s: Service)

    @Query("SELECT * FROM services")
    suspend fun getAll(): List<Service>

    @Query("SELECT * FROM services WHERE id = :id")
    suspend fun getService(id: Int): Service


}