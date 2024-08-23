package com.example.playlistmaker

import com.google.gson.annotations.SerializedName

data class Track(
  @SerializedName("trackName") val trackName: String,
  @SerializedName("artistName") val artistName: String,
  @SerializedName("trackTime") val trackTime: String,
  @SerializedName("artworkUrl100") val artworkUrl100: String,
)