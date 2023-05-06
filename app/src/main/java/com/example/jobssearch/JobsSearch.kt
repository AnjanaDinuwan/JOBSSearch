package com.example.jobssearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NavUtils
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobssearch.data.MainDataSource
import com.example.jobssearch.data.model.Job
import kotlinx.coroutines.launch


class JobsSearch : AppCompatActivity() {
    val allJobsAdapter = AllJobsAdapter(listOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs_search)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.jobsearch_title)
        supportActionBar?.elevation = 0.0F


        val recyclerView = findViewById<RecyclerView>(R.id.rv_jobs)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = allJobsAdapter

        lifecycleScope.launch {
            MainDataSource.getAllJobs { result -> allJobsCallback(result)}
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

    fun allJobsCallback(dataset: List<MainDataSource.JobCompanyInfo>) {
        allJobsAdapter.dataset = dataset
        allJobsAdapter.notifyDataSetChanged()
    }

    class AllJobsAdapter(var dataset: List<MainDataSource.JobCompanyInfo>) :
        RecyclerView.Adapter<AllJobsAdapter.ViewHolder>() {
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