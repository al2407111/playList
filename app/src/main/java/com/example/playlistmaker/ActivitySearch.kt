package com.example.playlistmaker


import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.coroutines.launch





class ActivitySearch : AppCompatActivity() , TrackViewHolder.Listener{
    private lateinit var searchEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var backButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageImg: ImageView
    private lateinit var placeholderText: TextView
    private lateinit var updateButton: Button
    private lateinit var adapter: TrackAdapter
    private lateinit var placeholderLayout: LinearLayout
    private lateinit var recyclerView2: RecyclerView
    private lateinit var cleanButton: Button

    private lateinit var historyMain: LinearLayout
    private lateinit var searchHistory: SearchHistory
    private lateinit var savedTrackAdapter: TrackAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchEditText = findViewById(R.id.input_editText)
        clearButton = findViewById(R.id.clear_icon)
        backButton = findViewById(R.id.button_back)
        recyclerView = findViewById(R.id.track_list)
        placeholderLayout = findViewById(R.id.placeholder_message)
        messageImg = findViewById(R.id.message_img)
        placeholderText = findViewById(R.id.message_text)
        updateButton = findViewById(R.id.update_button)
        recyclerView2 = findViewById(R.id.recyclerView)
        cleanButton=findViewById(R.id.clear_history_button)
        historyMain = findViewById(R.id.historyMain)


        historyMain.visibility= View.GONE




        managementBack()
        setupSearch()
        setupRecyclerView()
        historu()

    }

    private fun managementBack() {
//        val backToMainSettings = findViewById<ImageView>(R.id.button_back)//Закрытие
//        backToMainSettings.setOnClickListener {
//            finish()
//        }
        backButton.setOnClickListener {
            finish()
        }
        clearButton.visibility = View.GONE

        // нажатиe иконки
        clearButton.setOnClickListener {
            // Сброс

            clearEditText(searchEditText)


        }

    }

    private fun setupSearch() {

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // ВЫПОЛНЯЙТЕ ПОИСК

                performSearch()
                closeHideKeyboard()
                true
            } else {
                false

            }

        }

        clearButton.setOnClickListener {
            searchEditText.text.clear()
            clearButton.visibility = View.GONE
           // searchEditText.setFocusable(false)
            closeSearch()
            closeHideKeyboard()
            searchEditText.clearFocus()

        }
// отлслеживания фокуса
        searchEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus && searchHistory.savesTracks.size>0 ) {
                historyMain.visibility= View.VISIBLE

            } else {
                historyMain.visibility= View.GONE
            }
        }


        searchEditText.addTextChangedListener {
            if (searchEditText.text.isNullOrEmpty()) {//проверка на наличие строки
                clearButton.visibility = View.GONE
                historyMain.visibility= View.VISIBLE

            } else {
                clearButton.visibility = View.VISIBLE
            }
        }

        updateButton.setOnClickListener {
            performSearch()
        }

    }





    private fun setupRecyclerView() {
     adapter = TrackAdapter(emptyList(),this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun performSearch() {
        val term = searchEditText.text.toString().trim()
        if (term.isNotEmpty()) {
            historyMain.visibility= View.GONE
            lifecycleScope.launch {
                try {
                    val response = iTunesApiService.searchTracks(term)


                    if (response.results.isEmpty()) {
                        //  Плейсхолдер (нет результатов)
                        recyclerView.visibility = View.GONE
                        placeholderLayout.visibility = View.VISIBLE
                        messageImg.setImageResource(R.drawable.error_search)
                        placeholderText.text = getString(R.string.nothing_found)
                        updateButton.visibility = View.GONE

                        historyMain.visibility= View.GONE
                    } else {
                        showResults(response.results)
                    }
                } catch (e: Exception) {
                    //Плейсхолдер (ошибка сервера)
                    recyclerView.visibility = View.GONE
                    placeholderLayout.visibility = View.VISIBLE
                    messageImg.setImageResource(R.drawable.error_connect)
                    placeholderText.text = getString(R.string.err_connect)
                    updateButton.visibility = View.VISIBLE

                }
            }
        } else {
            closeSearch()

        }
    }


    private fun showResults(tracks: List<Track>) {

        adapter.updateTracks(tracks)

        recyclerView.visibility = View.VISIBLE
        placeholderLayout.visibility = View.GONE
      //  historyMain.visibility= View.GONE
    }


    private fun clearEditText(inputEditText: EditText?) {
        inputEditText?.setText(null)
        val inputMethodManager =
      this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(inputEditText?.windowToken, 0)
       // searchEditText.clearFocus()

    }

    // убрать список
    private fun closeSearch() {
        recyclerView.visibility = View.GONE
        placeholderLayout.visibility = View.GONE
    }

    fun closeHideKeyboard() {
        val hideKeyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hideKeyboard.hideSoftInputFromWindow(searchEditText.windowToken, 0)
    }
    private companion object {
        private val trackList = ArrayList<Track>()
        const val SEARCH_HISTORY = "Search history"

    }

    override fun onClickTrackHolder(track: Track) {
        val sharedPrefs = getSharedPreferences(SEARCH_HISTORY, MODE_PRIVATE)
        searchHistory = SearchHistory(sharedPrefs)
        searchHistory.saveTrack(track)
        val list = arrayListOf(track)

    }
    fun historu(){
        val  sharedPrefs = getSharedPreferences(SEARCH_HISTORY, MODE_PRIVATE)
       val trackAdapter = TrackAdapter(trackList, this)
        searchHistory = SearchHistory(sharedPrefs)
        savedTrackAdapter = TrackAdapter(searchHistory.getSavedTracks(), this)
        recyclerView2.setItemViewCacheSize(searchHistory.savesTracks.size)
        recyclerView2.adapter = savedTrackAdapter

        cleanButton.setOnClickListener {
        searchHistory.cleanHistory()
       historyMain.visibility= View.GONE


        }
    }
}