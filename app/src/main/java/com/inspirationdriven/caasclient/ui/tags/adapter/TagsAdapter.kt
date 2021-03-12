package com.inspirationdriven.caasclient.ui.tags.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class TagsAdapter(private val layoutInflater: LayoutInflater, private val listener: Listener) :
    ListAdapter<String, TagViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder =
        TagViewHolder(
            layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false),
            listener
        )

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        getItem(position)?.also { tag ->
            holder.bind(tag)
        }
    }

    interface Listener : TagViewHolder.Listener
}