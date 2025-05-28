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
import com.example.projectmcsdojo.models.Movie

class MovieListAdapter(
    var data: MutableList<Movie>,
    var ctx: Context
) : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    fun updateMovies(newMovies: List<Movie>) {
        data.clear()
        data.addAll(newMovies)
        notifyDataSetChanged()
    }

    class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val ivPoster: ImageView = itemView.findViewById(R.id.ivPoster)
        val txtPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val btnDetail: Button = itemView.findViewById(R.id.btnDetail)

        fun bind(movie: Movie, ctx: Context) {
            txtTitle.text = movie.title
            txtPrice.text = "Rp. ${movie.price}"

            // Load poster image
            Glide.with(ctx)
                .load(movie.linkImg)
                .into(ivPoster)

            // Handle detail button click
            btnDetail.setOnClickListener {
                val intent = Intent(ctx, MovieDetailPage::class.java).apply {
                    putExtra("movie_id", movie.id)
                }
                ctx.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val itemView = LayoutInflater.from(ctx).inflate(R.layout.list_item_movie, parent, false)
        return MovieListViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movie = data[position]
        holder.bind(movie, ctx)
    }
}
