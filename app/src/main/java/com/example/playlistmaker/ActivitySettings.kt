package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ActivitySettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backToMainSettings = findViewById<ImageView>(R.id.button_back)
        val shareButton = findViewById<TextView>(R.id.share_setting)
        val helpButton = findViewById<TextView>(R.id.help_setting)
        val userAgreementButton = findViewById<TextView>(R.id.userAgreement_setting)


        backToMainSettings.setOnClickListener {
            finish()
        }

        shareButton.setOnClickListener {
            shareApp()
        }
        helpButton.setOnClickListener {
            helpApp()
        }
        userAgreementButton.setOnClickListener {
            openUserAgreement()
        }
    }

    private fun shareApp() {
        val message = getString(R.string.app_share_message)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = getString(R.string.sharing_type)
        }
        val shareChooser = Intent.createChooser(shareIntent, null)
        startActivity(shareChooser)
    }

    private fun helpApp() {
        val message = getString(R.string.mail_text)
        val myEmail = getString(R.string.mail_address)
        val subject = getString(R.string.mail_subject)
        //"mailto:"
        val mailtoIntent = Intent(Intent.ACTION_SENDTO).apply{
      data = Uri.parse("mailto:$myEmail")
       putExtra(Intent.EXTRA_EMAIL, arrayOf(myEmail))
       putExtra(Intent.EXTRA_SUBJECT, subject)
     putExtra(Intent.EXTRA_TEXT, message)
    }
    startActivity(mailtoIntent)
}
    private fun openUserAgreement() {
        val url = getString(R.string.url_offer)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }


}