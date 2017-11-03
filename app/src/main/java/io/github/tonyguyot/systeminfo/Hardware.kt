package io.github.tonyguyot.systeminfo

import android.os.Build

/**
 * Manage information about the device.
 */
class Hardware {

    val manufacturer: String
        get() = Build.MANUFACTURER

    val product: String
        get() = Build.PRODUCT

    val model: String
        get() = Build.MODEL

    val fullProductName: String
        get() = "$manufacturer $product $model"

    val hardware: String
        get() = Build.HARDWARE

    val brand: String
        get() = Build.BRAND
}
