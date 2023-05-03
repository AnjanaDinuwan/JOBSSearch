package com.example.jobssearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobssearch.data.ExampleDataSource
import com.example.jobssearch.data.model.Job
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    var recommendedJobsAdapter = RecommendedJobsAdapter(listOf<Job>())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.rv_recommended_jobs);
        recyclerView.adapter = recommendedJobsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        ExampleDataSource.getRecommendedJobs { dataset -> recommendedJobsCallback(dataset) }

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
    }

    fun recommendedJobsCallback(dataset: List<Job>) {
        recommendedJobsAdapter.dataset = dataset
        recommendedJobsAdapter.notifyDataSetChanged()
    }

    class RecommendedJobsAdapter(var dataset: List<Job>) :
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

            fun bind(job : Job) {
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

