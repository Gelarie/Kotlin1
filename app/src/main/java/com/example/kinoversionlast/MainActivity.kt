package com.example.kinoversionlast

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import android.widget.Toast
import kotlinx.android.synthetic.main.film_item.*


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        val films = Film.List()

        val recyclerView : RecyclerView = findViewById(R.id.list_films)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = FilmAdapter(films,this,
                onItemClick = { (name,genre,description,country,year,picture) ->
            //val intent = Intent(this@MainActivity, plotFilm::class.java)
            startActivity(intent)
        })

        launch(UI)
        {
            val cacheFilms = loadFilmsFromDatabase(application as App).await()

            if (cacheFilms.isNotEmpty())
            {
                films.addAll(cacheFilms)

                recyclerView.adapter.notifyDataSetChanged()
            }
            else
            {
                val internetFilmsJob = loadFilmsFromInternet()
                internetFilmsJob.start()

                val internetFilms = internetFilmsJob.await()
                saveFilms(application as App, internetFilms)

                films.addAll(internetFilms)

                recyclerView.adapter.notifyDataSetChanged()
            }
        }
    }

    fun ButtonClick1(v: View) {

        val img1: ImageView = findViewById(R.id.imageView)
        img1.visibility = RecyclerView.VISIBLE
        val btn2: Button = findViewById(R.id.btn2)
        btn2.visibility = RecyclerView.VISIBLE
        val btn1: Button = findViewById(R.id.btn1)
        btn1.visibility = RecyclerView.GONE

    }
    fun ButtonClick2 (v: View) {
        val img1: ImageView = findViewById(R.id.imageView)
        img1.visibility = RecyclerView.GONE
        val btn2: Button = findViewById(R.id.btn2)
        btn2.visibility = RecyclerView.GONE
        val btn1: Button = findViewById(R.id.btn1)
        btn1.visibility = RecyclerView.VISIBLE
    }

}







