package com.example.jobssearch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobssearch.data.MainDataSource
import com.example.jobssearch.data.model.Job
import com.example.jobssearch.data.model.Provider
import kotlinx.coroutines.launch
import javax.xml.transform.ErrorListener


class ProviderHome : AppCompatActivity() {
    var providerAdapter = ProviderAdapter(listOf()) { id -> onCompanyCardClick(id) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_home)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.companies_title)
        supportActionBar?.elevation = 0.0F

        val recyclerView = findViewById<RecyclerView>(R.id.rv_companies)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = providerAdapter

        lifecycleScope.launch {
            MainDataSource.getAllProviders { result -> providerCallback(result) }
        }
    }

    fun onCompanyCardClick(id: Int) {
        val intent = Intent(this, CompanyDetails::class.java)
        intent.putExtra("ID", id)
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

    fun providerCallback(dataset: List<Provider>) {
        providerAdapter.dataset = dataset
        providerAdapter.notifyDataSetChanged()
    }

    class ProviderAdapter(var dataset: List<Provider>, val callback: (Int) -> Unit) :
        RecyclerView.Adapter<ProviderAdapter.ViewHolder>() {
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val txtCompanyName : TextView
            val txtLocation : TextView
            val txtDescription : TextView
            init {
                txtCompanyName = view.findViewById<TextView>(R.id.txt_company_name)
                txtLocation = view.findViewById<TextView>(R.id.txt_location)
                txtDescription = view.findViewById<TextView>(R.id.txt_company_desc)
            }

            fun bind(provider : Provider) {
                txtCompanyName.text = provider.companyName
                txtLocation.text = provider.location
                txtDescription.text = provider.description
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.company_card_layout, parent, false)

            return ViewHolder(view);
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val job = dataset[position]
            holder.bind(job)
            holder.itemView.setOnClickListener {
                callback(job.id)
            }
        }

        override fun getItemCount(): Int {
            return dataset.size
        }

    }
}