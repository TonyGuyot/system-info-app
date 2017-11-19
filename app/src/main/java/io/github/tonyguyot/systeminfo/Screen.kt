package io.github.tonyguyot.systeminfo

import android.util.DisplayMetrics
import android.view.WindowManager
import android.content.Context
import android.content.res.Configuration

/**
 * Manage information about the screen.
 */
class Screen(windowManager: WindowManager, context: Context) {

    companion object {
        const val STANDARD_DENSITY = 160 // 1dp = 1px @160 dpi
    }

    private val metrics = DisplayMetrics()

    private val configuration: Configuration

    init {
        windowManager.defaultDisplay.getMetrics(metrics)
        configuration = context.resources.configuration
    }

    /** X and Y dimensions of the screen in pixels */
    val dimensionsInPixels: String
        get() = "${metrics.widthPixels} × ${metrics.heightPixels} pixels"

    val size: String
        get() = when (configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
            Configuration.SCREENLAYOUT_SIZE_SMALL -> "Small"
            Configuration.SCREENLAYOUT_SIZE_NORMAL -> "Normal"
            Configuration.SCREENLAYOUT_SIZE_LARGE -> "Large"
            Configuration.SCREENLAYOUT_SIZE_XLARGE -> "xLarge"
            else -> "Undefined"
        }

    /** density of the screen in dpi */
    val density: Int
        get() = (metrics.density * STANDARD_DENSITY).toInt()

    val densityWithUnit: String
        get() = "$density dpi"

    val densityCategory: String
        get() = when (metrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW -> "LDPI"
            DisplayMetrics.DENSITY_MEDIUM -> "MDPI"
            DisplayMetrics.DENSITY_HIGH -> "HDPI"
            DisplayMetrics.DENSITY_TV -> "TVDPI"
            DisplayMetrics.DENSITY_XHIGH -> "XHDPI"
            DisplayMetrics.DENSITY_XXHIGH -> "XXHDPI"
            DisplayMetrics.DENSITY_XXXHIGH -> "XXXHDPI"
            else -> ""
        }

    val fullDensityInfo: String
        get() = if (densityCategory.isEmpty()) densityWithUnit else "$densityWithUnit ($densityCategory)"

    val fontScaling: String
        get() = "× ${metrics.scaledDensity / metrics.density}"

    fun isLandscape() = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun isPortrait() = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}
