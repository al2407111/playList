package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity(){
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            setContentView(R.layout.activity_main)


            val settingsButton = findViewById<Button>(R.id.button_settings)
            settingsButton.setOnClickListener {
            val settingsButtonIntent = Intent(this, ActivitySettings::class.java)
            startActivity(settingsButtonIntent)
            }

val mediaButton=findViewById<Button>(R.id.button_media)
            mediaButton.setOnClickListener{
                val mediaButtonIntent = Intent(this, ActivityMedia::class.java)
                startActivity(mediaButtonIntent)
            }
            val search=findViewById<Button>(R.id.button_search)
            search.setOnClickListener {
                val searchButtonIntent=Intent(this, ActivitySearch::class.java)
                startActivity(searchButtonIntent)
            }
        }
    }
