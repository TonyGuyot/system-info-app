package io.github.tonyguyot.systeminfo

import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Manage information about the screen.
 */
class Screen(windowManager: WindowManager) {

    companion object {
        const val STANDARD_DENSITY = 160 // 1dp = 1px @160 dpi
    }

    private val metrics = DisplayMetrics()

    init {
        windowManager.defaultDisplay.getMetrics(metrics);
    }

    /** X and Y dimensions of the screen in pixels */
    val dimensionsInPixels
        get() = "${metrics.widthPixels} × ${metrics.heightPixels} pixels"

    /** density of the screen in dpi */
    val density
        get() = "${metrics.density * STANDARD_DENSITY} dpi"

    val densityCategory
        get() = when (metrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW -> "ldpi"
            DisplayMetrics.DENSITY_MEDIUM -> "mdpi"
            DisplayMetrics.DENSITY_HIGH -> "hdpi"
            DisplayMetrics.DENSITY_TV -> "tvdpi"
            DisplayMetrics.DENSITY_XHIGH -> "xhdpi"
            DisplayMetrics.DENSITY_XXHIGH -> "xxhdpi"
            DisplayMetrics.DENSITY_XXXHIGH -> "xxxhdpi"
            else -> ""
        }

    val fullDensityInfo: String
        get() = if (densityCategory.isEmpty()) density else "$density ($densityCategory)"

    val fontScaling
        get() = metrics.scaledDensity
}
