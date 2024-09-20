package com.example.playlistmaker

import com.google.gson.annotations.SerializedName

data class Track(

  val trackName: String= "",      // Название композиции
  val artistName: String= "",     // Имя исполнителя
  var trackTime: Long=0,      // Продолжительность трека в формате mm:ss
  val trackTimeMillis: Int,  // Продолжительность трека в миллисекундах
  val artworkUrl100: String= "" ,  // Ссылка на изображение обложки

  val trackId: Int = 0,
  val collectionName : String,
  val releaseDate: String ,
  val primaryGenreName: String ,
  val country: String
)
{
  fun getCoverArtwork() = artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")
  fun getYear() = releaseDate.substringBefore('-')
}