package com.example.jobssearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class QuickjobCardLayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quickjob_card_layout)

        fun onServiceCardClick() {
            val intent = Intent(this, QuickJobHire::class.java)
            startActivity(intent)
        }
    }
}