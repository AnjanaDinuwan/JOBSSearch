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
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = arrayOf("soemthing", "nothing", "something", "something")

        val recyclerView = findViewById<RecyclerView>(R.id.rv_recommended_jobs);
        val recommendedJobsAdapter = RecommendedJobsAdapter(data)
        recyclerView.adapter = recommendedJobsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

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

    class RecommendedJobsAdapter(private val dataset: Array<String>) :
        RecyclerView.Adapter<RecommendedJobsAdapter.ViewHolder>() {
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            init {

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.job_card_layout, parent, false)

            return ViewHolder(view);
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        }

        override fun getItemCount(): Int {
            return dataset.size
        }

    }
}

