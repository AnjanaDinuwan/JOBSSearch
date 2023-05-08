package com.example.jobssearch

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.example.jobssearch.data.model.Seeker
import kotlinx.coroutines.launch
import java.io.File
import javax.xml.transform.ErrorListener


class SeekerHome : AppCompatActivity() {
    var seekerAdapter = SeekerAdapter(this, listOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeker_home)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.seekerhome_title)
        supportActionBar?.elevation = 0.0F

        val recyclerView = findViewById<RecyclerView>(R.id.rv_jobs)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = seekerAdapter

        lifecycleScope.launch {
            MainDataSource.getAllSeekers { result -> seekerCallback(result) }
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

    fun seekerCallback(dataset: List<Seeker>) {
        seekerAdapter.dataset = dataset
        seekerAdapter.notifyDataSetChanged()
    }

    class SeekerAdapter(private val context: Context, var dataset: List<Seeker>) :
        RecyclerView.Adapter<SeekerAdapter.ViewHolder>() {
        class ViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
            val txtSeekerName : TextView
            val imgLogo : ImageView
            init {
                txtSeekerName = view.findViewById<TextView>(R.id.emp_name)
                imgLogo = view.findViewById(R.id.img_logo)
            }

            fun bind(seeker : Seeker) {
                txtSeekerName.text = seeker.name


                if (seeker.profilePhoto != "") {
                    val imgFile = File(context.filesDir, seeker.profilePhoto)
                    if (imgFile.exists()) {
                        val logo = BitmapFactory.decodeFile(imgFile.absolutePath)
                        imgLogo.setImageBitmap(logo)
                    }
                }
                else {
                    imgLogo.setImageResource(R.drawable.ic_baseline_person_24)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.seeker_card_layout, parent, false)

            return ViewHolder(view, context);
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val seek = dataset[position]
            holder.bind(seek)

        }

        override fun getItemCount(): Int {
            return dataset.size
        }

    }
}