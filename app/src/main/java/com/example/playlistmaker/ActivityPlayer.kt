package com.example.playlistmaker

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import java.util.Locale

class ActivityPlayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_player)
        val backToMainSettings = findViewById<ImageView>(R.id.button_back)
        backToMainSettings.setOnClickListener {
            finish()
        }

        val intent = intent
        val gson = Gson()
        val track = gson.fromJson(intent.getStringExtra("track"), Track::class.java)
        val trackIcon = findViewById<ImageView>(R.id.track_icon)
        val trackName = findViewById<TextView>(R.id.track_name)
        val musicianName = findViewById<TextView>(R.id.artistName)
        val trackTiming = findViewById<TextView>(R.id.track_time)
        val collectionName=findViewById<TextView>(R.id.collection_name)
        val yearTrack=findViewById<TextView>(R.id.year_track)
        val genreTrack=findViewById<TextView>(R.id.genre_track)
        val countryTrack=findViewById<TextView>(R.id.country_track)

        val imageUrl = track.getCoverArtwork()
        val density = resources.displayMetrics.density


        trackName.text = track.trackName
        musicianName.text = track.collectionName
        trackTiming.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis.toInt())
        genreTrack.text=track.primaryGenreName
        countryTrack.text=track.country

    // yearTrack.text = track.getYear()

        // Название альбома
        val albumTrack : TextView = findViewById(R.id.collectionName)
        if (track.collectionName.isEmpty()) {
            collectionName.visibility = View.GONE
            albumTrack.visibility = View.GONE
        } else {
            collectionName.text = track.collectionName
            collectionName.visibility = View.VISIBLE
            albumTrack.visibility = View.VISIBLE
        }




        Glide.with(baseContext).load(imageUrl)
            .centerCrop()
            .transform(RoundedCorners((8 * density).toInt()))
            .placeholder(R.drawable.placeholder_312dp)
            .into(trackIcon)

        val releaseDate = track.releaseDate
        val year = releaseDate.substring(0, 4)
        yearTrack.text = year.takeIf { it.isNotEmpty() } ?: getString(R.string.noReply)
    }



}