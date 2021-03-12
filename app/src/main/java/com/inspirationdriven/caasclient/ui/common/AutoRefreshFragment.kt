package com.inspirationdriven.caasclient.ui.common

import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.inspirationdriven.caasclient.utils.NetworkHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.time.Duration
import javax.inject.Inject

@AndroidEntryPoint
abstract class AutoRefreshFragment : Fragment {

    @Inject
    lateinit var networkHelper: NetworkHelper

    protected abstract val errorView: TextView

    constructor() : super()

    constructor(layoutId: Int) : super(layoutId)

    protected fun scheduleNextRefresh(callback: () -> Unit) {
        lifecycleScope.launchWhenResumed {
            delay(AUTO_REFRESH_DURATION)
            callback.invoke()
        }
    }

    protected fun showError(exception: Exception) {
        if (networkHelper.isNetworkConnected()) {
            errorView.text = "Error: $exception"
        } else {
            errorView.text = "No internet connection"
        }
        errorView.isVisible = true
    }

    protected fun resetError() {
        errorView.isVisible = false
    }

    companion object {
        private val AUTO_REFRESH_DURATION = Duration.ofSeconds(10).toMillis()
    }

}