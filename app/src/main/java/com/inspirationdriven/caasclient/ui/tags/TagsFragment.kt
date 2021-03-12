package com.inspirationdriven.caasclient.ui.tags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.crazylegend.viewbinding.viewBinding
import com.inspirationdriven.caasclient.R
import com.inspirationdriven.caasclient.databinding.FragmentTagsBinding
import com.inspirationdriven.caasclient.ui.cats.CatsFragment
import com.inspirationdriven.caasclient.ui.common.AutoRefreshFragment
import com.inspirationdriven.caasclient.ui.tags.adapter.TagsAdapter
import com.inspirationdriven.caasclient.utils.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagsFragment : AutoRefreshFragment(R.layout.fragment_tags), TagsAdapter.Listener {

    override val errorView: TextView by lazy { binding.textError }

    private val binding by viewBinding(FragmentTagsBinding::bind)
    private val tagsViewModel: TagsFragmentViewModel by viewModels()
    private val tagsAdapter by lazy {
        TagsAdapter(
            LayoutInflater.from(requireContext()),
            this@TagsFragment
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.also { a ->
            with(a) {
                setSupportActionBar(binding.toolbar)
            }
        }

        setupRecyclerView()

        tagsViewModel.tags.observe(viewLifecycleOwner, { tagsResult ->
            binding.progressBar.isVisible =
                tagsResult is Resource.Loading && tagsAdapter.itemCount == 0

            if ((tagsResult is Resource.Success) or
                (tagsResult is Resource.Error)
            ) {
                scheduleNextRefresh {
                    tagsViewModel.getTags()
                }
            }

            when (tagsResult) {
                is Resource.Success -> {
                    resetError()
                    tagsAdapter.submitList(tagsResult.data)
                }
                is Resource.Error -> {
                    showError(tagsResult.exception)
                }
                else -> {
                    // Do nothing
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        tagsViewModel.getTags()
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tagsAdapter
        }
    }

    override fun onTagClick(tag: String) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment, CatsFragment.create(tag))
            .addToBackStack(null)
            .commit()
    }
}