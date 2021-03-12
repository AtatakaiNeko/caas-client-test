package com.inspirationdriven.caasclient.ui.cats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.inspirationdriven.caasclient.data.model.Cat
import com.inspirationdriven.caasclient.databinding.ListItemCatBinding

class CatsAdapter(private val layoutInflater: LayoutInflater, private val listener: Listener) :
    ListAdapter<Cat, CatViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cat>() {
            override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder =
        CatViewHolder(ListItemCatBinding.inflate(layoutInflater, parent, false), listener)

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        getItem(position)?.also { cat ->
            holder.bind(cat)
        }
    }

    interface Listener : CatViewHolder.Listener
}