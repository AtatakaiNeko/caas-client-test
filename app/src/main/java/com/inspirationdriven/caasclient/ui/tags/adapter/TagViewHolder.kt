package com.inspirationdriven.caasclient.ui.tags.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TagViewHolder(view: View, private val listener: Listener) : RecyclerView.ViewHolder(view) {

    fun bind(tag: String) {
        itemView.findViewById<TextView>(android.R.id.text1).text = tag
        itemView.setOnClickListener { listener.onTagClick(tag) }
    }

    interface Listener {
        fun onTagClick(tag: String)
    }
}