package io.github.tonyguyot.systeminfo

import android.os.Build

/**
 * Manage information about the system.
 */
class System {

    val release: String
        get() = "Android " + Build.VERSION.RELEASE

    val codeName: String
        get() = when (Build.VERSION.SDK_INT) {
            Build.VERSION_CODES.O -> "Oreo"
            Build.VERSION_CODES.N_MR1 -> "Nougat"
            Build.VERSION_CODES.N -> "Nougat"
            Build.VERSION_CODES.M -> "Marshmallow"
            Build.VERSION_CODES.LOLLIPOP_MR1 -> "Lollipop"
            Build.VERSION_CODES.LOLLIPOP -> "Lollipop"
            Build.VERSION_CODES.KITKAT -> "KitKat"
            Build.VERSION_CODES.JELLY_BEAN_MR2 -> "Jelly Bean"
            Build.VERSION_CODES.JELLY_BEAN_MR1 -> "Jelly Bean"
            Build.VERSION_CODES.JELLY_BEAN -> "Jelly Bean"
            Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 -> "Ice Cream Sandwich"
            Build.VERSION_CODES.ICE_CREAM_SANDWICH -> "Ice Cream Sandwich"
            else -> ""
        }

    val fullVersionName: String
        get() = if (codeName.isEmpty()) release else "$release ($codeName)"

    val sdkVersion: String
        get() = "API level " + Build.VERSION.SDK_INT

    /*
        Build.VERSION.BASE_OS
        Build.VERSION.SECURITY_PATCH
    */

}
