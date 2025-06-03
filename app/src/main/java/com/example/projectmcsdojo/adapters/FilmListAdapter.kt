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
import com.example.projectmcsdojo.FilmDetailPage
import com.example.projectmcsdojo.R
import com.example.projectmcsdojo.models.Film

class FilmListAdapter(
    var data: MutableList<Film>,
    var ctx: Context
) : RecyclerView.Adapter<FilmListAdapter.FilmListViewHolder>() {

    fun updateFilms(newFilms: List<Film>) {
        data.clear()
        data.addAll(newFilms)
        notifyDataSetChanged()
    }

    class FilmListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val ivPoster: ImageView = itemView.findViewById(R.id.ivPoster)
        val txtPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val btnDetail: Button = itemView.findViewById(R.id.btnDetail)

        fun bind(film: Film, ctx: Context) {
            txtTitle.text = film.film_title
            txtPrice.text = "Rp. ${film.film_price}"

            Glide.with(ctx)
                .load(film.film_image)
                .into(ivPoster)

            btnDetail.setOnClickListener {
                val intent = Intent(ctx, FilmDetailPage::class.java).apply {
                    putExtra("film_id", film.film_id)
                }
                ctx.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListViewHolder {
        val itemView = LayoutInflater.from(ctx).inflate(R.layout.list_item_film, parent, false)
        return FilmListViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FilmListViewHolder, position: Int) {
        val film = data[position]
        holder.bind(film, ctx)
    }
}
