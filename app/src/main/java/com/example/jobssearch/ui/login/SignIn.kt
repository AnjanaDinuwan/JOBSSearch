package com.example.jobssearch.ui.login

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.jobssearch.databinding.ActivitySignInBinding

import com.example.jobssearch.R
import kotlin.math.sign

class SignIn : AppCompatActivity() {
    var signInButton : Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        signInButton = findViewById<Button>(R.id.btn_login)
//        signInButton.text = "something"
        signInButton!!.setOnClickListener { testButton() }

        Log.d("Something", "here " )
    }

    fun testButton() {
        Log.d("what", "testing")
        signInButton!!.text = "something"

    }


}