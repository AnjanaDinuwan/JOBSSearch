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
        Job(0,0,"Fulltime","Web Designer","text1","LKR 50000",),

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
        var result : List<Job> = jobs.subList(0,10)
        callback(result)
    }
}