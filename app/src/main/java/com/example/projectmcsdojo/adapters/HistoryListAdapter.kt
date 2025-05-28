package com.example.projectmcsdojo.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectmcsdojo.MovieDetailPage
import com.example.projectmcsdojo.R
import com.example.projectmcsdojo.models.History
import com.example.projectmcsdojo.models.Movie
import com.example.projectmcsdojo.util.DB

class HistoryListAdapter(
    var data: MutableList<History>,
    var ctx: Context
) : RecyclerView.Adapter<HistoryListAdapter.HistoryListViewHolder>() {

//    fun updateMovies(newMovies: List<Movie>) {
//        data.clear()
//        data.addAll(newMovies)
//        notifyDataSetChanged()
//    }

    class HistoryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtHistoryTitle: TextView = itemView.findViewById(R.id.tvHistoryTitle)
        val ivHistoryPoster: ImageView = itemView.findViewById(R.id.ivHistoryPoster)
        val txtHistoryPrice: TextView = itemView.findViewById(R.id.tvHistoryPrice)
        val txtHistoryQuantity: TextView = itemView.findViewById(R.id.tvHistoryQuantity)

        fun bind(history: History, ctx: Context) {
            val movie = DB.getMovieById(ctx, history.filmId)

            if (movie != null) {
                txtHistoryTitle.text = movie.title
                txtHistoryPrice.text = "Rp. ${movie.price}"
                txtHistoryQuantity.text = "Quantity: ${history.quantity}"

                Glide.with(ctx)
                    .load(movie.linkImg)
                    .into(ivHistoryPoster)
            } else {
                // Movie not found - show placeholders or hide item
                txtHistoryTitle.text = "Unknown movie"
                txtHistoryPrice.text = "-"
                txtHistoryQuantity.text = "Quantity: ${history.quantity}"
                ivHistoryPoster.setImageResource(R.drawable.rounded_background)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryListViewHolder {
        val itemView = LayoutInflater.from(ctx).inflate(R.layout.list_item_history, parent, false)
        return HistoryListViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: HistoryListViewHolder, position: Int) {
        val history = data[position]
        holder.bind(history, ctx)
    }
}
