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





class ActivitySearch : AppCompatActivity() {
    private lateinit var searchEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var backButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageImg: ImageView
    private lateinit var placeholderText: TextView
    private lateinit var updateButton: Button
    private lateinit var adapter: TrackAdapter
    private lateinit var placeholderLayout: LinearLayout

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

        managementBack()
        setupSearch()
        setupRecyclerView()

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
            closeSearch()
                 closeHideKeyboard()
                // searchEditText.clearFocus()
        }



        searchEditText.addTextChangedListener {
            if (searchEditText.text.isNullOrEmpty()) {//проверка на наличие строки
                clearButton.visibility = View.GONE
            } else {
                clearButton.visibility = View.VISIBLE
            }
        }

        updateButton.setOnClickListener {
            performSearch()
        }
    }

    private fun setupRecyclerView() {
        adapter = TrackAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun performSearch() {
        val term = searchEditText.text.toString().trim()
        if (term.isNotEmpty()) {
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

    }


    private fun clearEditText(inputEditText: EditText?) {
        inputEditText?.setText(null)
        val inputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(inputEditText?.windowToken, 0)
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
}