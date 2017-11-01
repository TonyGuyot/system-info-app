package io.github.tonyguyot.systeminfo

import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Manage information about the screen.
 */
class Screen(windowManager: WindowManager) {

    private val metrics = DisplayMetrics()

    init {
        windowManager.getDefaultDisplay().getMetrics(metrics);
    }

    val dimensionsInPixels
        get() = "${metrics.widthPixels} × ${metrics.heightPixels}"

    val density
        get() = metrics.density
}
