package com.example.jobssearch.data

import android.content.Context
import android.net.Uri
import android.provider.Telephony.Mms.Rate
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.room.ColumnInfo
import com.example.jobssearch.data.dao.JobDao
import com.example.jobssearch.data.dao.ProviderDao
import com.example.jobssearch.data.dao.SeekerDao
import com.example.jobssearch.data.dao.ServiceDao
import com.example.jobssearch.data.model.Job
import com.example.jobssearch.data.model.Provider
import com.example.jobssearch.data.model.Seeker
import com.example.jobssearch.data.model.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

object MainDataSource {

    enum class LoginStatus {
        NOT_LOGGED_IN, SEEKER, PROVIDER
    }

    var signedIn: LoginStatus = LoginStatus.NOT_LOGGED_IN
    var db: AppDatabase? = null
    var jobDao: JobDao? = null
    var providerDao: ProviderDao? = null
    var seekerDao: SeekerDao? = null
    var serviceDao: ServiceDao? = null
    var userSeeker: Seeker? = null
    var userProvider: Provider? = null

    fun setDatabase(database: AppDatabase) {
        db = database
        jobDao = database.jobDao()
        providerDao = database.providerDao()
        seekerDao = database.seekerDao()
        serviceDao = database.serviceDao()
    }

    suspend fun validateSignIn(username: String, password:String): Boolean {
        Log.d("Something", username + ", " + password)
        val result1 = seekerDao?.authenticateSeeker(username, password)
        if (result1 != null) {
            signedIn = LoginStatus.SEEKER
            userSeeker = result1
            return true
        }

        val result2 = providerDao?.authenticateProvider(username, password)
        if (result2 != null) {
            signedIn = LoginStatus.PROVIDER
            userProvider = result2
            return true
        }

        return false
    }

    fun signOut() {
        signedIn = LoginStatus.NOT_LOGGED_IN
        userProvider = null
        userSeeker = null
    }

    suspend fun getRecommendedJobs(callback: (List<JobCompanyInfo>) -> Unit) {
        var result = jobDao?.getJobWithCompany() ?: listOf()
        callback(result)
    }

    suspend fun getAllJobs(callback: (List<JobCompanyInfo>) -> Unit) {
        var result = jobDao?.getJobWithCompany() ?: listOf()
        callback(result)
    }

    suspend fun addJob(companyId: Int, name: String, salary: String, description: String) {
        var j = Job(
            0,
            companyId,
            "Full",
            name,
            description,
            salary
        )
        jobDao?.insertJob(j)
    }

    suspend fun getAllProviders(callback: (List<Provider>) -> Unit) {
        var result = providerDao?.getAll() ?: listOf()
        callback(result)
    }

    suspend fun getAllSeekers(callback: (List<Seeker>) -> Unit) {
        var result = seekerDao?.getAll() ?: listOf()
        callback(result)
    }

    suspend fun getAllServices(callback: (List<Service>) -> Unit) {
        var result = serviceDao?.getAll() ?: listOf()
        callback(result)
    }

    suspend fun getProviderInfo(id: Int, callback: (Provider) -> Unit) {
        var result = providerDao?.getProvider(id) ?: Provider(
            0,
            "password",
            "testing@email.com",
            "Test",
            "Test",
            "",
            "example.com",
            "You should ideally never see this page. If you see this something is" +
                    "broken",
            "No where"
        )
        callback(result)
    }

    suspend fun getServiceInfo(id: Int, callback: (Service) -> Unit) {
        var result = serviceDao?.getService(id) ?: Service(
            0,
            "testing@email.com",
            "Test",
            "Test",
            "100",
            "example.com",
            "",
        )
        callback(result)
    }

    suspend fun getAllJobsForProvider(id: Int, callback: (List<Job>) -> Unit) {
        var result = providerDao?.getAllJobs(id) ?: listOf()
        callback(result)
    }

    fun moveFile(context: Context, initialFileUri: Uri, destination: String) {
        val destinationFile = File(context.filesDir, destination)
        val inputStream: InputStream? = context.contentResolver.openInputStream(initialFileUri)
        val outputStream: OutputStream = FileOutputStream(destinationFile)

        if (inputStream != null) {
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
        }
    }

    suspend fun addNewSeeker(context: Context, name: String, email: String, address: String, password: String,
                             photoUri: Uri, cvUri: Uri, callback: (Boolean) -> Unit) {

        Log.d("Something", "here")
        val photoUrl = UUID.randomUUID().toString()
        moveFile(context, photoUri, photoUrl)
        val cvUrl = UUID.randomUUID().toString()
        moveFile(context, cvUri, cvUrl)

        val seeker = Seeker(0, name, email, password, email, cvUrl, photoUrl)
        seekerDao?.insertSeeker(seeker)
        callback(true)
    }

    suspend fun addNewProvider(context: Context, name: String, email: String, address: String, password: String, description: String,
                             logoUri: Uri, callback: (Boolean) -> Unit) {

        Log.d("Something", "here - Provider")
        val logoUrl = UUID.randomUUID().toString()
        moveFile(context, logoUri, logoUrl)

        val provider = Provider(0, password, email, name, email, logoUrl, address, description, address)
        providerDao?.insertProvider(provider)
        callback(true)
    }

    suspend fun addNewService(context: Context,logoUri: Uri, name: String, email: String, address: String,rate: String,skills: String, callback: (Boolean) -> Unit) {


        val logoUrl = UUID.randomUUID().toString()
        moveFile(context, logoUri, logoUrl)

        val service = Service(0, name, email, address , rate, skills,logoUrl )
        serviceDao?.insertService(service)
        callback(true)
    }

    suspend fun updateSeeker(name: String, email: String, password: String, callback: () -> Unit) {
        withContext (Dispatchers.IO) {
            val seeker =
                userSeeker!!.copy(name = name, username = email, password = password, email = email)
            seekerDao?.updateSeeker(seeker)
            validateSignIn(seeker.username, seeker.password)
            callback()
        }
    }

    suspend fun updateProvider(name: String, email: String, password: String, callback: () -> Unit) {
        withContext (Dispatchers.IO) {
            val provider =
                userProvider!!.copy(companyName = name, userName = email, password = password, email = email)
            providerDao?.updateProvider(provider)
            validateSignIn(provider.userName, provider.password)
            callback()
        }
    }

    suspend fun searchJob(query: String, callback: (List<JobCompanyInfo>) -> Unit) {
        val result = jobDao?.searchQuery(query) ?: listOf()
        callback(result)
    }

    suspend fun searchProvider(query: String, callback: (List<Provider>) -> Unit) {
        val result = providerDao?.searchQuery(query) ?: listOf()
        callback(result)
    }

    suspend fun searchSeeker(query: String, callback: (List<Seeker>) -> Unit) {
        val result = seekerDao?.searchQuery(query) ?: listOf()
        callback(result)
    }

    suspend fun searchService(query: String, callback: (List<Service>) -> Unit) {
        val result = serviceDao?.searchQuery(query) ?: listOf()
        callback(result)
    }

    data class JobCompanyInfo (
        @ColumnInfo(name = "id") val id : Int?,
        @ColumnInfo(name = "company_name") val companyName : String?,
        @ColumnInfo(name = "name") val name: String?,
        @ColumnInfo(name = "description") val description : String?,
        @ColumnInfo(name = "logo") val logo: String?
    )
}