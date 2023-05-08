package com.example.jobssearch

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NavUtils
import androidx.lifecycle.lifecycleScope
import com.example.jobssearch.data.MainDataSource
import com.example.jobssearch.ui.login.SignIn
import kotlinx.coroutines.launch
import java.io.File

class UserProfile : AppCompatActivity() {
    var edtName : EditText? = null
    var edtEmail : EditText? = null
    var edtPassword : EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.update_profile)
        supportActionBar?.elevation = 0.0F

        edtName = findViewById(R.id.txt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        val imgProfilePhoto = findViewById<ImageView>(R.id.img_profile_photo)

        when (MainDataSource.signedIn) {
            MainDataSource.LoginStatus.NOT_LOGGED_IN -> finish()
            MainDataSource.LoginStatus.SEEKER -> {
                edtName?.setText(MainDataSource.userSeeker!!.name, TextView.BufferType.EDITABLE)
                edtEmail?.setText(MainDataSource.userSeeker!!.email, TextView.BufferType.EDITABLE)

                if (MainDataSource.userSeeker!!.profilePhoto != "") {
                    val imgFile = File(this.filesDir, MainDataSource.userSeeker!!.profilePhoto)
                    if (imgFile.exists()) {
                        val logo = BitmapFactory.decodeFile(imgFile.absolutePath)
                        imgProfilePhoto.setImageBitmap(logo)
                    }
                }
                else {
                    imgProfilePhoto.setImageResource(R.drawable.ic_baseline_person_24)
                }
            }
            MainDataSource.LoginStatus.PROVIDER -> {
                edtName?.setText(MainDataSource.userProvider!!.companyName, TextView.BufferType.EDITABLE)
                edtEmail?.setText(MainDataSource.userProvider!!.email, TextView.BufferType.EDITABLE)

                if (MainDataSource.userProvider!!.logo != "") {
                    val imgFile = File(this.filesDir, MainDataSource.userProvider!!.logo)
                    if (imgFile.exists()) {
                        val logo = BitmapFactory.decodeFile(imgFile.absolutePath)
                        imgProfilePhoto.setImageBitmap(logo)
                        imgProfilePhoto.imageTintList = null
                    }
                }
                else {
                    imgProfilePhoto.setImageResource(R.drawable.baseline_apartment_24)
                }
            }
        }

        val btnPhotoUpload = findViewById<Button>(R.id.btn_change_photo)
        btnPhotoUpload.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        val updateBtn = findViewById<Button>(R.id.btn_update)
        updateBtn.setOnClickListener {
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

            var password: String = edtPassword?.text.toString() ?: ""
            if (password == "") {
                password = when (MainDataSource.signedIn) {
                    MainDataSource.LoginStatus.NOT_LOGGED_IN -> ""
                    MainDataSource.LoginStatus.SEEKER -> MainDataSource.userSeeker!!.password
                    MainDataSource.LoginStatus.PROVIDER -> MainDataSource.userProvider!!.password
                }
            }

            when (MainDataSource.signedIn) {
                MainDataSource.LoginStatus.NOT_LOGGED_IN -> finish()
                MainDataSource.LoginStatus.SEEKER -> {
                    lifecycleScope.launch {
                        Log.d("Something", "$name, $email, $password")
                        MainDataSource.updateSeeker(name, email, password) { -> updateCallback() }
                    }
                }
                MainDataSource.LoginStatus.PROVIDER -> {
                    lifecycleScope.launch {
                        MainDataSource.updateProvider(name, email, password) { -> updateCallback() }
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_update_profile, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun updateCallback() {
        finish()
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
            R.id.action_signout -> {
                MainDataSource.signOut()
                val intent = Intent(this@UserProfile, SignIn::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selecter URI: $uri")
//            photoUri = uri
        }
        else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
}