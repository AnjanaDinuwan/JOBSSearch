package com.example.jobssearch

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NavUtils
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobssearch.data.MainDataSource
import com.example.jobssearch.data.model.Job
import com.example.jobssearch.data.model.Provider
import kotlinx.coroutines.launch
import java.io.File


class CompanyDetails : AppCompatActivity() {
    var txtCompanyName : TextView? = null
    var txtDescription : TextView? = null
    var txtLocation : TextView? = null
    var txtEmail : TextView? = null
    var txtWebsite : TextView? = null
    var imgLogo : ImageView? = null
    var jobAdapter : JobAdapter = JobAdapter(listOf())
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.empty)
        supportActionBar?.elevation = 0.0F

        id = intent.getIntExtra("ID", 0)

        txtCompanyName = findViewById(R.id.txt_company_name)
        txtDescription = findViewById(R.id.txt_company_desc)
        txtLocation = findViewById(R.id.txt_location)
        txtWebsite = findViewById(R.id.txt_website)
        txtEmail = findViewById(R.id.txt_email)
        imgLogo = findViewById(R.id.img_company_logo)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_vacancies)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = jobAdapter

        lifecycleScope.launch {
            MainDataSource.getProviderInfo(id) { provider -> companyInfoCallback(provider) }
            MainDataSource.getAllJobsForProvider(id) { jobs -> companyJobsCallback(jobs) }
        }


        val plusBtn2 = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btn_plus2);
        plusBtn2.setOnClickListener{
            val intent = Intent(this, PublishVacancy::class.java )
            startActivity(intent)
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

    fun companyInfoCallback(provider: Provider) {
        txtCompanyName?.text = provider.companyName
        txtDescription?.text = provider.description
        txtLocation?.text = provider.location
        txtWebsite?.text = provider.contact
        txtEmail?.text = provider.email

        if (provider.logo != "") {
            val imgFile = File(this.filesDir, provider.logo)
            if (imgFile.exists()) {
                val logo = BitmapFactory.decodeFile(imgFile.absolutePath)
                imgLogo?.setImageBitmap(logo)
            }
        }
    }

    fun companyJobsCallback(jobs: List<Job>) {
        jobAdapter.dataset = jobs

    }

    class JobAdapter(var dataset: List<Job>) :
        RecyclerView.Adapter<JobAdapter.ViewHolder>() {
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val txtJobName : TextView
            val txtDescription : TextView
            val txtSalary : TextView
            init {
                txtJobName = view.findViewById<TextView>(R.id.txt_job_name)
                txtDescription = view.findViewById<TextView>(R.id.txt_job_desc)
                txtSalary = view.findViewById(R.id.txt_salary)
            }

            fun bind(job : Job) {
                txtJobName.text = job.name
                txtDescription.text = job.description
                txtSalary.text = job.salary
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.vacancy_card, parent, false)

            return ViewHolder(view);
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(dataset[position])
        }

        override fun getItemCount(): Int {
            return dataset.size
        }
    }
}