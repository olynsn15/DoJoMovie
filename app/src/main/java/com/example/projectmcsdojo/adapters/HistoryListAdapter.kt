package com.example.projectmcsdojo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectmcsdojo.R
import com.example.projectmcsdojo.models.History
import com.example.projectmcsdojo.util.DB

class HistoryListAdapter(
    var data: MutableList<History>,
    var ctx: Context
) : RecyclerView.Adapter<HistoryListAdapter.HistoryListViewHolder>() {

    class HistoryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtHistoryTitle: TextView = itemView.findViewById(R.id.tvHistoryTitle)
        val ivHistoryPoster: ImageView = itemView.findViewById(R.id.ivHistoryPoster)
        val txtHistoryPrice: TextView = itemView.findViewById(R.id.tvHistoryPrice)
        val txtHistoryQuantity: TextView = itemView.findViewById(R.id.tvHistoryQuantity)

        fun bind(history: History, ctx: Context) {
            val film = DB.getFilmById(ctx, history.film_id)

            if (film != null) {
                txtHistoryTitle.text = film.film_title
                txtHistoryPrice.text = "Rp. ${film.film_price}"
                txtHistoryQuantity.text = "Quantity: ${history.quantity}"

                Glide.with(ctx)
                    .load(film.film_image)
                    .into(ivHistoryPoster)
            } else {
                txtHistoryTitle.text = "Unknown film"
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
