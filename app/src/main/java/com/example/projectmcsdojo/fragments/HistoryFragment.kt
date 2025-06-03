package com.example.projectmcsdojo.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmcsdojo.R
import com.example.projectmcsdojo.adapters.HistoryListAdapter
import com.example.projectmcsdojo.util.DB

class HistoryFragment : Fragment() {
    private lateinit var title: TextView

    private lateinit var rvHistoryList: RecyclerView
    private lateinit var historyAdapter: HistoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        title = view.findViewById(R.id.history_title)

        val spannable = SpannableString("History.")
        spannable.setSpan(
            ForegroundColorSpan(Color.YELLOW),
            spannable.length - 1,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        title.text = spannable

        rvHistoryList = view.findViewById(R.id.rvHistoryList)
        rvHistoryList.layoutManager = LinearLayoutManager(requireContext())
        historyAdapter = HistoryListAdapter(mutableListOf(), requireContext())
        rvHistoryList.adapter = historyAdapter

        loadHistory()

        return view
    }

    override fun onResume() {
        super.onResume()
        loadHistory()
    }

    private fun loadHistory() {
        val user = DB.LOGGED_IN_USER
        if (user == null) {
            return
        }

        val historyList = DB.getTransactionHistoryForUser(requireContext(), user.user_id)

        historyAdapter.data.clear()
        historyAdapter.data.addAll(historyList)
        historyAdapter.notifyDataSetChanged()
    }
}
