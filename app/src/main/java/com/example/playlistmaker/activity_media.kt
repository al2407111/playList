package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_media :  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        val backToMainMedia = findViewById<Button>(R.id.button_back)
        backToMainMedia.setOnClickListener {
            val backToMainIntent = Intent(this, MainActivity::class.java)
            startActivity(backToMainIntent)
        }
    }
}