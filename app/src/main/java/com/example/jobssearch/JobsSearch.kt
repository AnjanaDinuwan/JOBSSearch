package com.example.jobssearch

import android.content.Context
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NavUtils
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.jobssearch.data.MainDataSource
import kotlinx.coroutines.launch
import java.io.File


class JobsSearch : AppCompatActivity() {
    val allJobsAdapter = AllJobsAdapter(this, listOf())
    var swipeRefresh : SwipeRefreshLayout? = null
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

        val edtSearch = findViewById<EditText>(R.id.edt_search)
        swipeRefresh = findViewById(R.id.swiperefresh)
        swipeRefresh?.setOnRefreshListener {
            lifecycleScope.launch {
                val query = edtSearch.text.toString()
                if (query == "") {
                    MainDataSource.getAllJobs { result -> allJobsCallback(result)}
                }
                else {
                    MainDataSource.searchJob(query) { result -> allJobsCallback(result) }
                }
            }
        }

        edtSearch.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                lifecycleScope.launch {
                    if (query == "") {
                        MainDataSource.getAllJobs { result -> allJobsCallback(result)}
                    }
                    else {
                        MainDataSource.searchJob(query) { result -> allJobsCallback(result) }
                    }
                }
            }

        })

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
        swipeRefresh?.isRefreshing = false
        allJobsAdapter.dataset = dataset
        allJobsAdapter.notifyDataSetChanged()
    }

    class AllJobsAdapter(private val context: Context, var dataset: List<MainDataSource.JobCompanyInfo>) :
        RecyclerView.Adapter<AllJobsAdapter.ViewHolder>() {
        class ViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
            val txtCompanyName : TextView
            val txtJobName : TextView
            val txtDescription : TextView
            val imgLogo = view.findViewById<ImageView>(R.id.img_logo)
            init {
                txtCompanyName = view.findViewById<TextView>(R.id.txt_company_name)
                txtJobName = view.findViewById<TextView>(R.id.edt_job_name)
                txtDescription = view.findViewById<TextView>(R.id.txt_job_desc)
            }

            fun bind(job : MainDataSource.JobCompanyInfo) {
                txtJobName.text = job.name
                txtCompanyName.text = job.companyName
                txtDescription.text = job.description

                if (job.logo != "") {
                    val imgFile = File(context.filesDir, job.logo)
                    if (imgFile.exists()) {
                        val logo = BitmapFactory.decodeFile(imgFile.absolutePath)
                        imgLogo.setImageBitmap(logo)
                    }
                }
                else {
                    imgLogo.setImageResource(R.drawable.baseline_apartment_24)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.job_card_layout, parent, false)

            return ViewHolder(view, context);
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(dataset[position])
        }

        override fun getItemCount(): Int {
            return dataset.size
        }
    }
}