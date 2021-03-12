package com.inspirationdriven.caasclient.ui.cats.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.crazylegend.viewbinding.viewBinding
import com.inspirationdriven.caasclient.R
import com.inspirationdriven.caasclient.data.model.Cat
import com.inspirationdriven.caasclient.databinding.FragmentCatDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatDetailsFragment : Fragment(R.layout.fragment_cat_details) {
    private val binding by viewBinding(FragmentCatDetailsBinding::bind)

    private val cat by lazy { arguments?.getParcelable<Cat>(ARG_CAT) }

    companion object {
        private const val ARG_CAT = "cat"

        fun create(cat: Cat) =
            CatDetailsFragment()
                .apply {
                    arguments = bundleOf(ARG_CAT to cat)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (cat == null) {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cat?.also {
            Glide.with(this)
                .load(it.getUrl())
                .fitCenter()
                .into(binding.imgCat)
        }
    }
}