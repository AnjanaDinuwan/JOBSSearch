package com.example.jobssearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NavUtils
import androidx.lifecycle.lifecycleScope
import com.example.jobssearch.data.MainDataSource
import kotlinx.coroutines.launch

class PublishVacancy : AppCompatActivity() {
    var edtJobName: EditText? = null
    var edtSalary: EditText? = null
    var edtDescription: EditText? = null
    var companyId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish_vacancy)

        companyId = intent.getIntExtra("COMPANY_ID", 0)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.publish_vacancy)
        supportActionBar?.elevation = 0.0F

        edtJobName = findViewById(R.id.edt_job_name)
        edtSalary = findViewById(R.id.edt_salary)
        edtDescription = findViewById(R.id.edt_description)

        val btnAddJob = findViewById<Button>(R.id.btn_add_job)
        btnAddJob.setOnClickListener {
            val name: String = edtJobName?.text.toString() ?: ""
            if (name == "") {
                Toast.makeText(this, "Name cannot be null", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val salary: String = edtSalary?.text.toString() ?: ""
            if (salary == "") {
                Toast.makeText(this, "Salary cannot be null", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val description: String = edtDescription?.text.toString() ?: ""
            if (description == "") {
                Toast.makeText(this, "Description cannot be null", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                MainDataSource.addJob(companyId, name, salary, description)
                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (parentActivityIntent == null) {
                    Log.i(
                        "something",
                        "You have forgotten to specify the parentActivityName in the AndroidManifest!"
                    )
                    onBackPressed()
                } else {
                    NavUtils.navigateUpFromSameTask(this)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}