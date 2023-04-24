package com.example.jobssearch.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.jobssearch.MainActivity

import com.example.jobssearch.R
import com.example.jobssearch.data.ExampleDataSource
import com.example.jobssearch.provider_registration
import com.example.jobssearch.seeker_registration

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        val signInButton = findViewById<Button>(R.id.btn_signin)
        signInButton!!.setOnClickListener {
            signIn();
        }

        val guestButton = findViewById<Button>(R.id.button10)
        guestButton.setOnClickListener {
            val intent = Intent(this, MainActivity:: class.java)
            startActivity(intent)

        }

        val seekerButton = findViewById<Button>(R.id.btn_seeker_registration)
        seekerButton.setOnClickListener {
            val intent = Intent(this, seeker_registration:: class.java)
            startActivity(intent)

        }

        val providerButton = findViewById<Button>(R.id.btn_provider_registration)
        providerButton.setOnClickListener {
            val intent = Intent(this, provider_registration:: class.java)
            startActivity(intent)

        }


    }

    fun signIn() {
        val usernameTextView = findViewById<TextView>(R.id.username)
        val passwordTextView = findViewById<TextView>(R.id.password)
        val username = usernameTextView.text.toString()
        val password = passwordTextView.text.toString()

        val result = ExampleDataSource.validateSignIn(username, password)

        if (result) {
            Toast.makeText(this@SignIn,
                "Sign in success", Toast.LENGTH_SHORT).show()
            usernameTextView.text = ""
            passwordTextView.text = ""
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else {
            Toast.makeText(this@SignIn,
                "Sign In failed", Toast.LENGTH_SHORT).show()
        }

    }

}