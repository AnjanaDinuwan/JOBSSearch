package com.example.jobssearch.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jobssearch.data.dao.JobDao
import com.example.jobssearch.data.dao.ProviderDao
import com.example.jobssearch.data.dao.SeekerDao
import com.example.jobssearch.data.model.Job
import com.example.jobssearch.data.model.Provider
import com.example.jobssearch.data.model.Seeker

@Database(entities= [Job::class, Provider::class, Seeker::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun jobDao(): JobDao
    abstract fun providerDao(): ProviderDao
    abstract fun seekerDao(): SeekerDao
}