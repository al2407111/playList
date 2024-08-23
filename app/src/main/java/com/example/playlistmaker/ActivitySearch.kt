package com.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.playlistmaker.api.itunessearch.ItunesSearchApi
import com.example.playlistmaker.api.itunessearch.ItunesSearchResponse
import com.example.playlistmaker.api.itunessearch.SearchMessageCode

class ActivitySearch : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrackAdapter
    private lateinit var placeholderMessage: LinearLayout
    private lateinit var messageImg: ImageView
    private lateinit var messageText: TextView
    private lateinit var messageButton: Button

    companion object {
        const val SEARCH_EDIT_TEXT = "SEARCH_EDIT_TEXT"
        const val SEARCH_EDIT_TEXT_RETAIN = ""
        const val SEARCH_URL = "https://itunes.apple.com"

    }


    // Переменная для хранения текста поискового запроса
    private var searhEditText: String = SEARCH_EDIT_TEXT_RETAIN

    // Сервис поиска треков
    private val itunesSearchBaseUrl = SEARCH_URL
    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesSearchBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val itunesSearchService = retrofit.create(ItunesSearchApi::class.java)

    private var tracks = ArrayList<Track>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val backToMainSettings = findViewById<ImageView>(R.id.button_back)//Закрытие
        backToMainSettings.setOnClickListener {
            finish()
        }


        val inputEditText = findViewById<EditText>(R.id.input_editText)
        val clearButton = findViewById<ImageView>(R.id.clear_icon)
         var placeholderMessage=findViewById<LinearLayout>(R.id.placeholderMessage)
         var messageImg=findViewById<ImageView >(R.id.messageImg)
         var messageText=findViewById<TextView>(R.id.messageText)
       var messageButton=findViewById<Button>(R.id.messageButton)


        clearButton.visibility=View.GONE
        // нажатиe иконки
        clearButton.setOnClickListener {
            // Сброс
            clearEditText(inputEditText)
        }
        // изменения поля EditText
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {//проверка на наличие строки
                    clearButton.visibility=View.GONE
                } else {
                    clearButton.visibility=View.VISIBLE
                }
                searhEditText = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }

        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
        recyclerView = findViewById(R.id.trackList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TrackAdapter(tracks)
        recyclerView.adapter = adapter

    }





    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Сохраняется значение переменной searhEditText с текстом поискового запроса
        outState.putString(SEARCH_EDIT_TEXT, searhEditText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Сохраненный текст
        val inputEditText = findViewById<EditText>(R.id.input_editText)
        searhEditText = savedInstanceState.getString(SEARCH_EDIT_TEXT, SEARCH_EDIT_TEXT_RETAIN)
        inputEditText.setText(searhEditText)
    }


    private fun clearEditText(inputEditText: EditText?) {
        inputEditText?.setText(null)
        val inputMethodManager =
            this.getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(inputEditText?.windowToken, 0)
    }
    private fun setupRecyclerView() {
        adapter = TrackAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


}
