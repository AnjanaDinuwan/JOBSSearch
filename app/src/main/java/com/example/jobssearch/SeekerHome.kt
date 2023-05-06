package com.example.jobssearch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SeekerHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeker_home)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.seekerhome_title)
        supportActionBar?.elevation = 0.0F

        val recyclerView = findViewById<RecyclerView>(R.id.rv_jobs)
        val tList = ArrayList<Int>()
        tList.add(1)
        tList.add(2)
        tList.add(3)
        tList.add(4)
        val seekerAdapter = SeekerHome.SeekerAdapter(this, tList) { -> onSeekerCardClick() }
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = seekerAdapter

    }

     fun onSeekerCardClick() {
         val intent = Intent(this, UpdatePosition::class.java)
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


    class SeekerAdapter(private val context: Context, private val seekerArrayList: ArrayList<Int>, private val listener: () -> Unit) :
        RecyclerView.Adapter<SeekerAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var seekerCard: RelativeLayout
            init {
                seekerCard = view.findViewById<RelativeLayout>(R.id.seeker_card)
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.seeker_card_layout, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.seekerCard.setOnClickListener{
                listener()
            }
        }

        override fun getItemCount() = seekerArrayList.size
    }
}




