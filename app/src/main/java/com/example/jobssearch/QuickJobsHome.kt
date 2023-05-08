package com.example.jobssearch

import android.content.Context
import android.content.Intent
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
import android.widget.TextView
import androidx.core.app.NavUtils
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.jobssearch.data.MainDataSource
import com.example.jobssearch.data.model.Provider
import com.example.jobssearch.data.model.Service
import kotlinx.coroutines.launch


class QuickJobsHome : AppCompatActivity() {
    var swipeRefresh : SwipeRefreshLayout? = null
    var jobAdapter = JobAdapter(this, listOf()) { id -> onServiceCardClick(id) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_jobs_home)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.quickjobs_title)
        supportActionBar?.elevation = 0.0F

        val recyclerView = findViewById<RecyclerView>(R.id.rv_jobs)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = jobAdapter

        lifecycleScope.launch {
            MainDataSource.getAllServices { result -> providerCallback(result) }
        }

        val plusBtn =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btn_plus);
        plusBtn.setOnClickListener {
            val intent = Intent(this, AddService::class.java)
            startActivity(intent)
        }

        val edtSearch = findViewById<EditText>(R.id.edt_search)
        swipeRefresh = findViewById(R.id.swiperefresh)
        swipeRefresh?.setOnRefreshListener {
            lifecycleScope.launch {
                val query = edtSearch.text.toString()
                if (query == "") {
                    MainDataSource.getAllServices { result -> providerCallback(result)}
                }
                else {
                    MainDataSource.searchService(query) { result -> providerCallback(result) }
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
                        MainDataSource.getAllServices { result -> providerCallback(result)}
                    }
                    else {
                        MainDataSource.searchService(query) { result -> providerCallback(result) }
                    }
                }
            }
        })
    }

    fun onServiceCardClick(id: Int) {
        val intent = Intent(this, QuickJobHire::class.java)
        intent.putExtra("SERVICES_ID", id)
        startActivity(intent)
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

    fun providerCallback(dataset: List<Service>) {
        swipeRefresh?.isRefreshing = false
        jobAdapter.dataset = dataset
        jobAdapter.notifyDataSetChanged()
    }

    class JobAdapter(
        private val context: Context,
        var dataset: List<Service>,
        val callback: (Int) -> Unit
    ) :
        RecyclerView.Adapter<JobAdapter.ViewHolder>() {

        class ViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
            val txtproviderName: TextView
            val txtLocation: TextView
            val txtRate: TextView
            val txtDescription: TextView
            //          val imgLogo: ImageView

            init {
                txtproviderName = view.findViewById<TextView>(R.id.txt_name)
                txtLocation = view.findViewById<TextView>(R.id.txt_location)
                txtRate = view.findViewById<TextView>(R.id.txt_rate)
                txtDescription = view.findViewById<TextView>(R.id.txt_company_desc)
                //               imgLogo = view.findViewById(R.id.img_logo)
            }

            fun bind(service: Service) {
                txtproviderName.text = service.name
                txtLocation.text = service.address
                txtRate.text = service.rate
                txtDescription.text = service.skills

//                if (service.logo != "") {
//                    val imgFile = File(context.filesDir, service.logo)
//                    if (imgFile.exists()) {
//                        val logo = BitmapFactory.decodeFile(imgFile.absolutePath)
//                        imgLogo.setImageBitmap(logo)
//                    }
//                } else {
//                    imgLogo.setImageResource(R.drawable.ic_baseline_person_24)
//                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_quickjob_card_layout, parent, false)
            return ViewHolder(view, context)
        }

        override fun getItemCount() = dataset.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val service = dataset[position]
            holder.bind(service)
            holder.itemView.setOnClickListener {
                callback(service.id)
            }
        }


    }
}