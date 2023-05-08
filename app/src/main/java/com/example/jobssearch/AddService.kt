package com.example.jobssearch

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NavUtils
import androidx.lifecycle.lifecycleScope
import com.example.jobssearch.data.MainDataSource
import kotlinx.coroutines.launch

class AddService : AppCompatActivity() {

    var logoUri : Uri? = null
    var edtName : EditText? = null
    var edtEmail : EditText? = null
    var edtAddress : EditText? = null
    var edtRate : EditText? = null
    var edtSkills : EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_service)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.Add_Service)
        supportActionBar?.elevation = 0.0F

        edtName = findViewById(R.id.emp_name)
        edtEmail = findViewById(R.id.edt_email)
        edtAddress = findViewById(R.id.edt_address)
        edtRate = findViewById(R.id.contact)
        edtSkills = findViewById(R.id.edt_description)

        val btnLogoUpload = findViewById<LinearLayout>(R.id.btn_logo_upload)
        btnLogoUpload.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        val btnService = findViewById<Button>(R.id.btn_add_job)

        btnService.setOnClickListener {
            val name: String = edtName?.text.toString() ?: ""
            if (name == "") {
                Toast.makeText(this, "Name cannot be null", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val email: String = edtEmail?.text.toString() ?: ""
            if (email == "") {
                Toast.makeText(this, "Email cannot be null", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val address: String = edtAddress?.text.toString() ?: ""
            if (address == "") {
                Toast.makeText(this, "Address cannot be null", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rate: String = edtRate?.text.toString() ?: ""
            if (rate == "") {
                Toast.makeText(this, "Rate cannot be null", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val skills : String = edtSkills?.text.toString() ?: ""
            if (skills == "") {
                Toast.makeText(this, "Skills cannot be null", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (logoUri == null) {
                Toast.makeText(this, "Profile photo not chosen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            val context = this

            lifecycleScope.launch {
                MainDataSource.addNewService(

                    context,
                    logoUri!!,
                    name,
                    email,
                    address,
                    rate,
                    skills,

                )
                { result -> isSuccessCallback(result) }
            }
        }


    }

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selecter URI: $uri")
            logoUri = uri
        }
        else {
            Log.d("PhotoPicker", "No media selected")
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



    fun isSuccessCallback(result: Boolean) {
        Log.d("Something", "The result is $result")
        if (result) {
            finish()
        }
        else {
            Toast.makeText(this, "Something went wrong please try again!", Toast.LENGTH_LONG).show()
        }
    }

}


