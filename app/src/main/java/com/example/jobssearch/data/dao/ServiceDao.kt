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


}