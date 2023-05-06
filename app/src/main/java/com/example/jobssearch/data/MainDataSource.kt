package com.example.jobssearch.data

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.room.ColumnInfo
import com.example.jobssearch.data.dao.JobDao
import com.example.jobssearch.data.dao.ProviderDao
import com.example.jobssearch.data.dao.SeekerDao
import com.example.jobssearch.data.model.Job
import com.example.jobssearch.data.model.Provider
import com.example.jobssearch.data.model.Seeker
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

object MainDataSource {

    var signedIn: Boolean = false
    var db: AppDatabase? = null
    var jobDao: JobDao? = null
    var providerDao: ProviderDao? = null
    var seekerDao: SeekerDao? = null

    var seekers : MutableList<Seeker> = mutableListOf(
        Seeker(0,"Anjana Munasinghe", "Anjana", "password", "anjana@gmail.com","",""),
    )
    var providers : MutableList<Provider> = mutableListOf(
        Provider(0,"password","pasan@gmail.com","Microsoft","Microsoft","","","",""),
        Provider(1,"password","bill@gmail.com","Amazon","Amazon","","","",""),
        Provider(2,"password","anthony@gmail.com","Google","Google","","","",""),
        Provider(3,"password","mercedes@gmail.com","Virtusa","Virtusa","","","",""),
    )
    var jobs : MutableList<Job> = mutableListOf(
        Job(0,2, "Fulltime","Web Designer","text1","LKR 50000"),
        Job(1,0, "Fulltime","UI/UX Designer","text1","LKR 50000"),
        Job(2,1, "Fulltime","Machine learning engineer","text1","LKR 20000"),
        Job(3,0,  "Fulltime","Software engineer","text1","LKR 100000"),
        Job(4,0,"Fulltime","Tech lead","text1","LKR 500000"),
    )

    fun setDatabase(database: AppDatabase) {
        db = database
        jobDao = database.jobDao()
        providerDao = database.providerDao()
        seekerDao = database.seekerDao()
    }

    fun validateSignIn(username: String, password:String): Boolean {
        Log.d("Something", username + ", " + password)
        for (seeker in seekers) {
            if (seeker.username == username && seeker.password == password) {
                signedIn = true
                return true
            }
        }
        return false
    }

    suspend fun getRecommendedJobs(callback: (List<JobCompanyInfo>) -> Unit) {
        var result = jobDao?.getJobWithCompany() ?: listOf()
        callback(result)
    }

    suspend fun getAllJobs(callback: (List<JobCompanyInfo>) -> Unit) {
        var result = jobDao?.getJobWithCompany() ?: listOf()
        callback(result)
    }

    suspend fun insertJob(name: String, companyId: Int) {
        var j = Job(0, companyId,
            "something", name, "Lorem Ipsum blala", "$5000")
        jobDao?.insertJob(j)
    }

    suspend fun getAllProviders(callback: (List<Provider>) -> Unit) {
        var result = providerDao?.getAll() ?: listOf()
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

        val seeker = Seeker(0, name, name, password, email, cvUrl, photoUrl)
        seekerDao?.insertSeeker(seeker)
        callback(true)
    }

    suspend fun addNewProvider(context: Context, name: String, email: String, address: String, password: String, description: String,
                             logoUri: Uri, callback: (Boolean) -> Unit) {

        Log.d("Something", "here - Provider")
        val logoUrl = UUID.randomUUID().toString()
        moveFile(context, logoUri, logoUrl)

        val provider = Provider(0, password, email, name, name, logoUrl, address, description, address)
        providerDao?.insertProvider(provider)
        callback(true)
    }

    data class JobCompanyInfo (
        @ColumnInfo(name = "id") val id : Int?,
        @ColumnInfo(name = "company_name") val companyName : String?,
        @ColumnInfo(name = "name") val name: String?,
        @ColumnInfo(name = "description") val description : String?,
        @ColumnInfo(name = "logo") val logo: String?
    )
}