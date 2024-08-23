package com.example.playlistmaker


import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackAdapter(private var tracks: List<Track>) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private val trackName: TextView = itemView.findViewById(R.id.trackName)
       private val artistName: TextView = itemView.findViewById(R.id.artistName)
       private val trackTime: TextView = itemView.findViewById(R.id.trackTime)
        private val trackImage: ImageView = itemView.findViewById(R.id.trackImage)


      private val imgRadius = dpToPx(2F, itemView.context)



        fun bind(track: Track) {
            trackName.text = track.trackName
         artistName.text = track.artistName
            trackTime.text = track.trackTime

            Glide.with(itemView.context)
                .load(track.artworkUrl100)
                .placeholder(R.drawable.ic_placeholder_image)
                .centerCrop()
                .transform(RoundedCorners(imgRadius))
                .into(trackImage)
        }
        private fun dpToPx(dp: Float, context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.resources.displayMetrics).toInt()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount() = tracks.size

    fun updateTracks(newTracks: List<Track>) {
        tracks = newTracks
        notifyDataSetChanged()
    }

}