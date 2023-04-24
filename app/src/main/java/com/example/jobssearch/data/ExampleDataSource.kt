package com.example.jobssearch.data

import android.util.Log

object ExampleDataSource {

    fun validateSignIn(username: String, password:String): Boolean {
        Log.d("Something", username + ", " + password)
        if (username == "Anjana" && password == "password123") {
            return true
        }
        return false
    }
}