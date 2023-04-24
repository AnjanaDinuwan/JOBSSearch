package com.example.jobssearch.data

import android.util.Log
import com.example.jobssearch.data.model.Seeker

object ExampleDataSource {

    var signedIn: Boolean = false

    var seekers : MutableList<Seeker> = mutableListOf(
        Seeker("Anjana Munasinghe", "Anjana",
            "password", "anjana@gmail.com"),
        Seeker("Pasan Munasinghe", "Pasan",
            "password", "pasan@gmail.com")
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
}