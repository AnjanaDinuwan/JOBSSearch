package com.example.jobssearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class UpdatePosition : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_position)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.UpdatePosition_title)
        supportActionBar?.elevation = 0.0F

    }
}