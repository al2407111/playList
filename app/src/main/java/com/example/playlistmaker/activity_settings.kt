package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class activity_settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val backToMainSettings = findViewById<Button>(R.id.button_back)
        backToMainSettings.setOnClickListener {
            val backToMainIntent = Intent(this, MainActivity::class.java)
            startActivity(backToMainIntent)
        }

    }
}