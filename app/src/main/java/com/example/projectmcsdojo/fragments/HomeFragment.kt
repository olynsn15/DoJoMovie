package com.example.projectmcsdojo.fragments

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.projectmcsdojo.R
import com.example.projectmcsdojo.adapters.FilmListAdapter
import com.example.projectmcsdojo.databinding.FragmentHomeBinding
import com.example.projectmcsdojo.util.DB
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap
    private lateinit var filmAdapter: FilmListAdapter

    override fun onCreateView(
        inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Styled "Location" text
        val textLO = "Location"
        val spannableLO = SpannableString(textLO)
        spannableLO.setSpan(UnderlineSpan(), 0, spannableLO.length, 0)
        binding.tvLocation.text = spannableLO

        // Styled "Available Films."
        val textAF = "Available Films."
        val yellowColor = ContextCompat.getColor(requireContext(), R.color.yellow)
        val spannableAF = SpannableString(textAF)
        spannableAF.setSpan(
            ForegroundColorSpan(yellowColor),
            textAF.length - 1,
            textAF.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvAvailableFilms.text = spannableAF

        // RecyclerView setup
        filmAdapter = FilmListAdapter(mutableListOf(), requireContext())
        binding.rvFilmList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFilmList.adapter = filmAdapter

        // Load movie data from API
        val safeContext = requireContext() // Ambil context yang valid
        fetchFilmsFromAPI(safeContext) {
            DB.syncFilms(safeContext)
            filmAdapter.updateFilms(DB.filmList)
        }


        // Set up Google Map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val dojo = LatLng(-6.2088, 106.8456)
        mMap.addMarker(MarkerOptions().position(dojo).title("This is DoJo Movie store"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dojo, 15f))
    }

    private fun fetchFilmsFromAPI(context: Context, onComplete: () -> Unit) {
        val url = "https://api.npoint.io/66cce8acb8f366d2a508"
        val queue = Volley.newRequestQueue(context)

        val request = JsonArrayRequest(url,
            { response ->
                for (i in 0 until response.length()) {
                    val obj = response.getJSONObject(i)
                    val film_id = obj.getString("id")
                    val film_title = obj.getString("title")
                    val film_image = obj.getString("image")
                    val film_price = obj.getInt("price")
                    DB.insertFilms(context, film_id, film_title, film_image, film_price)
                }
                onComplete()
            },
            { error ->
                error.printStackTrace()
                onComplete()
            })

        queue.add(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
