package com.example.jobssearch

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.jobssearch.data.MainDataSource
import com.example.jobssearch.ui.login.SignIn
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {
    var recommendedJobsAdapter = RecommendedJobsAdapter(this, listOf<MainDataSource.JobCompanyInfo>())
    var swipeRefresh : SwipeRefreshLayout? = null
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

        val profileBtn = findViewById<Button>(R.id.btn_profile)
        when (MainDataSource.signedIn) {
            MainDataSource.LoginStatus.NOT_LOGGED_IN -> profileBtn.text = "Sign In"
            MainDataSource.LoginStatus.SEEKER -> profileBtn.text = MainDataSource.userSeeker!!.username.substringBefore("@")
            MainDataSource.LoginStatus.PROVIDER -> profileBtn.text = MainDataSource.userProvider!!.userName.substringBefore("@")
        }
        profileBtn.setOnClickListener {
            var intent: Intent = when (MainDataSource.signedIn) {
                MainDataSource.LoginStatus.NOT_LOGGED_IN -> Intent(this, SignIn::class.java)
                MainDataSource.LoginStatus.SEEKER -> Intent(this, UserProfile::class.java)
                MainDataSource.LoginStatus.PROVIDER -> Intent(this, UserProfile::class.java)
            }
            if (MainDataSource.signedIn == MainDataSource.LoginStatus.NOT_LOGGED_IN) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }

        swipeRefresh = findViewById(R.id.swiperefresh)
        swipeRefresh?.setOnRefreshListener {
            lifecycleScope.launch {
                MainDataSource.getRecommendedJobs { dataset -> recommendedJobsCallback(dataset) }
            }
        }
    }

    fun recommendedJobsCallback(dataset: List<MainDataSource.JobCompanyInfo>) {
        swipeRefresh?.isRefreshing = false
        recommendedJobsAdapter.dataset = dataset
        recommendedJobsAdapter.notifyDataSetChanged()
    }

    class RecommendedJobsAdapter(private val context: Context, var dataset: List<MainDataSource.JobCompanyInfo>) :
        RecyclerView.Adapter<RecommendedJobsAdapter.ViewHolder>() {
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

