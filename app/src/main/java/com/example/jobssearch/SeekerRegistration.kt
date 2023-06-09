package com.example.jobssearch

import android.app.Activity
import android.content.Intent
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

class SeekerRegistration : AppCompatActivity() {
    var photoUri : Uri? = null
    var cvUri : Uri? = null
    var edtName : EditText? = null
    var edtEmail : EditText? = null
    var edtAddress : EditText? = null
    var edtPassword : EditText? = null
    var edtConfirmPassword : EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeker_registration)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.empty)
        supportActionBar?.elevation = 0.0F

        edtName = findViewById(R.id.emp_name)
        edtEmail = findViewById(R.id.edt_email)
        edtAddress = findViewById(R.id.edt_address)
        edtPassword = findViewById(R.id.edt_password)
        edtConfirmPassword = findViewById(R.id.edt_confirm_password)

        val btnPhotoUpload = findViewById<LinearLayout>(R.id.btn_photo_upload)
        btnPhotoUpload.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        val btnCVUpload = findViewById<LinearLayout>(R.id.btn_cv_upload)
        btnCVUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/pdf"
            }
            pickCVFile.launch(intent)
        }

        val btnRegister = findViewById<Button>(R.id.btn_add_job)
        btnRegister.setOnClickListener {
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

            val password: String = edtPassword?.text.toString() ?: ""
            val passwordConfirm: String = edtConfirmPassword?.text.toString() ?: ""
            if (password == "") {
                Toast.makeText(this, "Password cannot be null", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != passwordConfirm) {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (photoUri == null) {
                Toast.makeText(this, "Profile photo not chosen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (cvUri == null) {
                Toast.makeText(this, "CV/Resume not chosen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val context = this

            lifecycleScope.launch {
                MainDataSource.addNewSeeker(
                    context,
                    name,
                    email,
                    address,
                    password,
                    photoUri!!,
                    cvUri!!
                )
                { result -> isSuccessCallback(result) }
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

    fun isSuccessCallback(result: Boolean) {
        Log.d("Something", "The result is $result")
        if (result) {
            finish()
        }
        else {
            Toast.makeText(this, "Something went wrong please try again!", Toast.LENGTH_LONG).show()
        }
    }

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selecter URI: $uri")
            photoUri = uri
        }
        else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    val pickCVFile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val data: Uri? = result.data?.data
            if (data != null) {
                cvUri = data
                Log.d("Something", "Data is $data")
            }
        }
        else {
            Log.d("Something", "No data was returned")
        }
    }
}