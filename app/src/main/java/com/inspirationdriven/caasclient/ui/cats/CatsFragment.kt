package com.inspirationdriven.caasclient.ui.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.crazylegend.viewbinding.viewBinding
import com.inspirationdriven.caasclient.R
import com.inspirationdriven.caasclient.data.model.Cat
import com.inspirationdriven.caasclient.databinding.FragmentCatsBinding
import com.inspirationdriven.caasclient.ui.cats.adapter.CatsAdapter
import com.inspirationdriven.caasclient.ui.cats.details.CatDetailsFragment
import com.inspirationdriven.caasclient.ui.common.AutoRefreshFragment
import com.inspirationdriven.caasclient.utils.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatsFragment : AutoRefreshFragment(R.layout.fragment_cats), CatsAdapter.Listener {

    override val errorView: TextView by lazy { binding.textError }

    private val binding by viewBinding(FragmentCatsBinding::bind)
    private val catViewModel: CatsFragmentViewModel by viewModels()

    private val selectedTag by lazy { arguments?.getString(ARG_TAG) ?: "" }

    private val catsAdapter by lazy {
        CatsAdapter(LayoutInflater.from(requireContext()), this)
    }

    companion object {
        private const val ARG_TAG = "tag"

        fun create(tag: String) =
            CatsFragment()
                .apply {
                    arguments = bundleOf(ARG_TAG to tag)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (selectedTag.isBlank()) {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.also { a ->
            with(a) {
                setSupportActionBar(binding.toolbar)
                supportActionBar?.apply {
                    setDisplayHomeAsUpEnabled(true)
                    setHomeButtonEnabled(true)
                }
            }
        }

        binding.toolbar.title = "Cats in $selectedTag"
        setupRecycler()

        catViewModel.cats.observe(viewLifecycleOwner, { result ->
            binding.progressBar.isVisible =
                result is Resource.Loading && catsAdapter.itemCount == 0

            if ((result is Resource.Success) or
                (result is Resource.Error)
            ) {
                scheduleNextRefresh {
                    catViewModel.getCats(selectedTag)
                }
            }
            when (result) {
                is Resource.Success -> {
                    catsAdapter.submitList(result.data)
                    resetError()
                    scheduleNextRefresh { catViewModel.getCats(selectedTag) }
                }
                is Resource.Error -> {
                    showError(result.exception)
                    scheduleNextRefresh { catViewModel.getCats(selectedTag) }
                }
                else -> {
                    // Do nothing
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        catViewModel.getCats(selectedTag)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                parentFragmentManager.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecycler() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = catsAdapter
        }
    }

    override fun onCatClick(cat: Cat) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment, CatDetailsFragment.create(cat))
            .addToBackStack(null)
            .commit()
    }

}