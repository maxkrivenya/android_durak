package com.example.pms.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pms.R
import com.example.pms.android.Stats

class StatsAdapter(private var statsList: List<Stats>) : RecyclerView.Adapter<StatsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val statsName: TextView = view.findViewById(R.id.stats_date)
        val statsValue: TextView = view.findViewById(R.id.stats_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stats_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stats = statsList[position]
        holder.statsName.text = stats.date.toString()
        holder.statsValue.text = stats.duration.toString()
    }

    fun updateItems(newItems: List<Stats>) {
        statsList = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount() = statsList.size
}