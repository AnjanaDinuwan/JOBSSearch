package com.example.jobssearch.data

import android.util.Log
import com.example.jobssearch.data.model.Job
import com.example.jobssearch.data.model.Provider
import com.example.jobssearch.data.model.Seeker

object ExampleDataSource {

    var signedIn: Boolean = false

    var seekers : MutableList<Seeker> = mutableListOf(
        Seeker(0,"Anjana Munasinghe", "Anjana", "password", "anjana@gmail.com","",""),
    )
    var providers : MutableList<Provider> = mutableListOf(
        Provider(0,"Pasan","pasan@gmail.com","Microsoft","Microsoft","","","",""),
    )
    var jobs : MutableList<Job> = mutableListOf(
        Job(0,0,null, null, "Fulltime","Web Designer","text1","LKR 50000"),
        Job(1,0,null, null, "Fulltime","UI/UX Designer","text1","LKR 50000"),
        Job(2,0,null, null, "Fulltime","Machine learning engineer","text1","LKR 20000"),
        Job(3,0,null, null, "Fulltime","Software engineer","text1","LKR 100000"),
        Job(4,0,null, null, "Fulltime","Tech lead","text1","LKR 500000"),

    )


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

    fun getRecommendedJobs(callback: (List<Job>) -> Unit) {
        var totalResults = 10;
        if (totalResults > jobs.size) {
            totalResults = jobs.size
        }
        var result : List<Job> = jobs.subList(0, totalResults)

        for (job in result) {
            for (provider in providers) {
                if (job.companyID == provider.id) {
                    job.companyName = provider.companyName
                    job.companyLogo = provider.logo
                }
            }
        }
        callback(result)
    }
}