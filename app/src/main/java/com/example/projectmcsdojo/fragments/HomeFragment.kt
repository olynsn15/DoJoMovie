package com.example.projectmcsdojo.fragments

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.projectmcsdojo.R
import com.example.projectmcsdojo.adapters.MovieListAdapter
import com.example.projectmcsdojo.util.DB
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class HomeFragment : Fragment(){

    private lateinit var rvMovieList: RecyclerView
    private lateinit var movieAdapter: MovieListAdapter

    private var mapView:MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Configuration.getInstance().load(
            requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext())
        )
        Configuration.getInstance().userAgentValue = requireContext().packageName

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val tvLocation = view.findViewById<TextView>(R.id.tvLocation)
        val textLO = "Location"
        val spannableLO = SpannableString(textLO)
        spannableLO.setSpan(UnderlineSpan(), 0, spannableLO.length, 0)
        tvLocation.text = spannableLO

        val tvAvailableFilms = view.findViewById<TextView>(R.id.tvAvailableFilms)
        val textAF = "Available Films."
        val yellowColor = ContextCompat.getColor(requireContext(), R.color.yellow)
        val spannableAF = SpannableString(textAF)
        spannableAF.setSpan(ForegroundColorSpan(yellowColor), textAF.length - 1, textAF.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvAvailableFilms.text = spannableAF

        mapView = view.findViewById(R.id.mapView)
        createMap()

        rvMovieList = view.findViewById(R.id.rvMovieList)
        rvMovieList.layoutManager = LinearLayoutManager(requireContext())
        movieAdapter = MovieListAdapter(mutableListOf(), requireContext())
        rvMovieList.adapter = movieAdapter

        fetchMoviesFromAPI(requireContext()) {
            DB.syncMovies(requireContext())
            movieAdapter.updateMovies(DB.movieList)
        }

        return view
    }

    private fun fetchMoviesFromAPI(context: Context, onComplete: () -> Unit) {
        val url = "https://api.npoint.io/66cce8acb8f366d2a508"
        val queue = Volley.newRequestQueue(context)

        val request = JsonArrayRequest(url,
            { response ->
                for (i in 0 until response.length()) {
                    val obj = response.getJSONObject(i)
                    val id = obj.getString("id")
                    val title = obj.getString("title")
                    val image = obj.getString("image")
                    val price = obj.getInt("price")
                    DB.insertMovie(context, id, title, image, price)
                }
                onComplete()
            },
            { error ->
                error.printStackTrace()
                onComplete()
            })

        queue.add(request)
    }

    private fun createMap() {
        mapView?.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)

            val mapController = controller
            mapController.setZoom(15.0)

            val jakartaPoint = GeoPoint(-6.2088, 106.8456) // Correct lat-lon
            mapController.setCenter(jakartaPoint)

            val marker = Marker(this)
            marker.position = jakartaPoint
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = "DoJo Movie"
            marker.subDescription = "This is DoJo Movie store"
            overlays.add(marker)
        }
    }
}
