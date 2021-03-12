package com.inspirationdriven.caasclient.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crazylegend.viewbinding.viewBinding
import com.inspirationdriven.caasclient.databinding.ActivityMainBinding
import com.inspirationdriven.caasclient.ui.tags.TagsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(binding.containerFragment.id, TagsFragment())
                .commit()
        }
    }
}