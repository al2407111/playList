package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.google.android.material.switchmaterial.SwitchMaterial

private const val EXAMPLE_PREFERENCES = "shared_preferences"
private const val SWITCH_THEME_KEY = "key_of_switch_theme"

class ActivitySettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)




        val backToMainSettings = findViewById<ImageView>(R.id.button_back)
        val shareButton = findViewById<TextView>(R.id.share_setting)
        val helpButton = findViewById<TextView>(R.id.help_setting)
        val userAgreementButton = findViewById<TextView>(R.id.userAgreement_setting)


        // Переключатель темы
        val themeSwitcher = findViewById<SwitchMaterial>(R.id.switcher)
        val themeSharedPreferences = getSharedPreferences(EXAMPLE_PREFERENCES, MODE_PRIVATE)
        themeSwitcher.isChecked = (application as App).darkTheme
        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)
            themeSharedPreferences.edit{
                putBoolean(SWITCH_THEME_KEY, checked)
                apply()
            }
        }






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