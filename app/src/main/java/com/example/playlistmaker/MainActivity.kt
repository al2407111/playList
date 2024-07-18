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
//            val massage: Button=findViewById(R.id.button_search)
//            massage.setOnClickListener {
//                Toast.makeText(this,"Кнопка1",Toast.LENGTH_SHORT).show()}
//                val massage2=findViewById<Button>(R.id.button_media)
//
////                val massageClick:View.OnClickListener= object : View.OnClickListener {
////                    override fun onClick(p0: View?) {
////                        Toast.makeText(this@MainActivity,"Кнопка2",Toast.LENGTH_SHORT).show()
////                    }
////                }
////                massage2.setOnClickListener(massageClick)
//
//            massage2.setOnClickListener{Toast.makeText(this,"Кнопка2",Toast.LENGTH_SHORT).show()}
//
//               val massage3=findViewById<Button>(R.id.button_settings)
//massage3.setOnClickListener{Toast.makeText(this,"Кнопка3",Toast.LENGTH_SHORT).show()
//            }

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
