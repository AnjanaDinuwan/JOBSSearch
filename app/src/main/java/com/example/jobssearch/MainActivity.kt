package com.example.jobssearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.jobssearch.data.AppDatabase
import com.example.jobssearch.data.MainDataSource
import com.example.jobssearch.data.model.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var recommendedJobsAdapter = RecommendedJobsAdapter(listOf<MainDataSource.JobCompanyInfo>())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_recommended_jobs);
        recyclerView.adapter = recommendedJobsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        lifecycleScope.launch {
//            ExampleDataSource.insertJob("soemthing", 1)
            MainDataSource.getRecommendedJobs { dataset -> recommendedJobsCallback(dataset) }
        }

        val companiesBtn = findViewById<LinearLayout>(R.id.btn_companies);
        companiesBtn.setOnClickListener{
            val intent = Intent(this, ProviderHome::class.java )
            startActivity(intent)
        }

        val seekersBtn = findViewById<LinearLayout>(R.id.btn_job_search)
        seekersBtn.setOnClickListener {
            val intent = Intent(this, SeekerHome::class.java)
            startActivity(intent)
        }

        val quickjobsBtn = findViewById<LinearLayout>(R.id.btn_quick_jobs)
        quickjobsBtn.setOnClickListener {
            val intent = Intent(this, QuickJobsHome::class.java)
            startActivity(intent)
        }

        val jobsearchBtn = findViewById<TextView>(R.id.txt_viewall)
        jobsearchBtn.setOnClickListener {
            val intent = Intent(this, JobsSearch::class.java)
            startActivity(intent)
        }

        val userprofile = findViewById<Button>(R.id.btn_profile)
        userprofile.setOnClickListener {
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }
    }

    fun recommendedJobsCallback(dataset: List<MainDataSource.JobCompanyInfo>) {
        recommendedJobsAdapter.dataset = dataset
        recommendedJobsAdapter.notifyDataSetChanged()
    }

    class RecommendedJobsAdapter(var dataset: List<MainDataSource.JobCompanyInfo>) :
        RecyclerView.Adapter<RecommendedJobsAdapter.ViewHolder>() {
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val txtCompanyName : TextView
            val txtJobName : TextView
            val txtDescription : TextView
            init {
                txtCompanyName = view.findViewById<TextView>(R.id.txt_company_name)
                txtJobName = view.findViewById<TextView>(R.id.txt_job_name)
                txtDescription = view.findViewById<TextView>(R.id.txt_job_desc)
            }

            fun bind(job : MainDataSource.JobCompanyInfo) {
                txtJobName.text = job.name
                txtCompanyName.text = job.companyName
                txtDescription.text = job.description
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.job_card_layout, parent, false)

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

