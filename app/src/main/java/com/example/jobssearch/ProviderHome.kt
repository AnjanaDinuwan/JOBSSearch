package com.example.jobssearch

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProviderHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_home)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_companies)
        val tList = ArrayList<Int>()
        tList.add(1)
        tList.add(2)
        tList.add(3)
        tList.add(4)
        val jobAdapter = SeekerHome.JobAdapter(this, tList)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = jobAdapter
    }

    class JobAdapter(private val context: Context, private val jobArrayList: ArrayList<Int>) :
        RecyclerView.Adapter<JobAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            init {

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.company_card_layout, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        }

        override fun getItemCount() = jobArrayList.size
    }
}