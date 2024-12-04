package com.example.pms.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.pms.R
import com.example.pms.android.Stats
import com.example.pms.ui.auth.Login.Companion.VolleySingleton
import com.google.android.material.button.MaterialButton
import org.json.JSONObject

class History : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StatsAdapter
    private var statsList: MutableList<Stats> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val sortByDateBtn       = view.findViewById<View>(R.id.sortByDateButton) as Button;
        val sortByDurationBtn   = view.findViewById<View>(R.id.sortByDurationButton) as Button;

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = StatsAdapter(statsList)
        recyclerView.adapter = adapter

        // Sample data
        statsList.addAll(getSampleStats())
        adapter.notifyDataSetChanged()

        sortByDateBtn.setOnClickListener {
            statsList.sortBy { it.date }
            adapter.notifyDataSetChanged()
        };

        sortByDurationBtn.setOnClickListener {
            statsList.sortBy { it.duration }
            adapter.notifyDataSetChanged()
        };

        
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getSampleStats(): List<Stats> {
        VolleySingleton.getInstance(this.requireContext());

            val url = "http://192.168.207.80:8080/api/user/stats/max/2023-05-05T10:15:30+01:00"
            val items = mutableListOf<Stats>()
            val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET, url, null,
                { response ->
                    Log.println(Log.INFO, "STATS", response.toString());
                    for (i in 0 until response.length()) {
                        val jsonObject: JSONObject = response.getJSONObject(i)
                        val userid      = jsonObject.getString("userid")
                        val date        = jsonObject.getString("date")
                        val duration    = jsonObject.getInt("duration")
                        items.add(Stats(userid, date.substring(0,10), duration))
                    }
                    updateRecyclerView(items)
                },
                { error ->
                    Log.println(Log.ERROR, "ERROR", error.toString());
                    // Handle the error
                }
            )

      //      // Add the request to the RequestQueue
            VolleySingleton.getRequestQueue().add(jsonArrayRequest)



    // Replace with your own data retrieval logic
        return items;
    }

    fun updateRecyclerView(items: List<Stats>) {
        // Update your RecyclerView adapter with the new list
        adapter.updateItems(items) // Implement this method in your adapter
    }

}