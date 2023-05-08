package com.example.jobssearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NavUtils
import androidx.lifecycle.lifecycleScope
import com.example.jobssearch.data.MainDataSource
import com.example.jobssearch.data.model.Service
import kotlinx.coroutines.launch

class QuickJobHire : AppCompatActivity() {
    var id: Int = 0
    var rate: Int = 0
    var hours: Int = 0
    var cost: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quick_job_hire)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.hire_service)
        supportActionBar?.elevation = 0.0F

        id = intent.getIntExtra("SERVICES_ID", 0)

        lifecycleScope.launch {
            MainDataSource.getServiceInfo(id) { service -> serviceCallback(service) }
        }

        val edtHours = findViewById<EditText>(R.id.edt_hours)
        edtHours.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.d("something", "hours is ${s.toString()}")
                hours = try { s.toString().toInt() } catch (e: java.lang.Exception) { 0 }
                cost = hours * rate
                val txtCost = findViewById<TextView>(R.id.txt_cost)
                txtCost.text = cost.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        val btnHire = findViewById<Button>(R.id.btn_hire)
        btnHire.setOnClickListener {
            if (hours > 0) {
                Toast.makeText(this, "Quotation sent as email", Toast.LENGTH_LONG).show()
                finish()
            }
            else {
                Toast.makeText(this, "Enter a valid number of hours", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun serviceCallback(service: Service) {

        var txtName = findViewById<TextView>(R.id.emp_name)
        var txtRate = findViewById<TextView>(R.id.txt_rate)
        var txtSkills = findViewById<TextView>(R.id.txt_skills)

        txtName.text = service.name
        txtRate.text = service.rate
        txtSkills.text = service.skills

        rate = service.rate.toInt()
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