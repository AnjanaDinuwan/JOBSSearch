package com.example.jobssearch.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.jobssearch.MainActivity

import com.example.jobssearch.R
import com.example.jobssearch.data.MainDataSource
import com.example.jobssearch.ProviderRegistration
import com.example.jobssearch.SeekerRegistration
import com.example.jobssearch.data.AppDatabase
import kotlinx.coroutines.launch

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        MainDataSource.setDatabase(
            Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "main.db"
        ).createFromAsset("main_db.db").build())

        if (MainDataSource.signedIn != MainDataSource.LoginStatus.NOT_LOGGED_IN) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


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
            val intent = Intent(this, SeekerRegistration:: class.java)
            startActivity(intent)

        }

        val providerButton = findViewById<Button>(R.id.btn_provider_registration)
        providerButton.setOnClickListener {
            val intent = Intent(this, ProviderRegistration:: class.java)
            startActivity(intent)

        }


    }

    fun signIn() {
        val usernameTextView = findViewById<TextView>(R.id.username)
        val passwordTextView = findViewById<TextView>(R.id.password)
        val username = usernameTextView.text.toString()
        val password = passwordTextView.text.toString()
        val context = this

        lifecycleScope.launch {
            val result = MainDataSource.validateSignIn(username, password)

            if (result) {
                Toast.makeText(
                    context,
                    "Sign in success", Toast.LENGTH_SHORT
                ).show()
                usernameTextView.text = ""
                passwordTextView.text = ""
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                Toast.makeText(
                    context,
                    "Sign In failed", Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

}