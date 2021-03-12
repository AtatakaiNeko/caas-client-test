package com.inspirationdriven.caasclient.ui.cats.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inspirationdriven.caasclient.R
import com.inspirationdriven.caasclient.data.model.Cat
import com.inspirationdriven.caasclient.databinding.ListItemCatBinding

class CatViewHolder(private val binding: ListItemCatBinding, private val listener: Listener) :
    RecyclerView.ViewHolder(binding.root) {

    private val previewHeight by lazy {
        itemView.resources.getDimensionPixelSize(R.dimen.cat_preview_size)
    }

    fun bind(cat: Cat) {

        binding.textCatId.text = cat.id
        binding.textCatTags.text = cat.tags.joinToString(", ")
        binding.textCreatedAt.text = cat.createdAt

        Glide.with(itemView)
            .asDrawable()
            .load(cat.getPreviewUrl(previewHeight))
            .placeholder(R.drawable.ic_cat_placeholder)
            .override(previewHeight, previewHeight)
            .centerCrop()
            .into(binding.imgCat)

        itemView.setOnClickListener { listener.onCatClick(cat) }
    }

    interface Listener {
        fun onCatClick(cat: Cat)
    }
}