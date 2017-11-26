/*
 * Copyright (C) 2017 Tony Guyot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.tonyguyot.systeminfo

import android.os.Build
import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import android.os.StatFs

/**
 * Manage information about the device.
 */
class Hardware(val context: Context) {

    private var memInfo: ActivityManager.MemoryInfo

    init {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        memInfo = ActivityManager.MemoryInfo()
        am.getMemoryInfo(memInfo)
    }

    val manufacturer: String
        get() = Build.MANUFACTURER

    val product: String
        get() = Build.PRODUCT

    val model: String
        get() = Build.MODEL

    val fullProductName: String
        get() = "${manufacturer.capitalize()} $model"

    val hardware: String
        get() = Build.HARDWARE

    val brand: String
        get() = Build.BRAND

    val board: String
        get() = Build.BOARD

    /** total RAM size in bytes */
    val totalMemory: Long
        get() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                return memInfo.totalMem
            } else {
                // TODO: use /proc/meminfo
                return 0L
            }
        }

    val totalMemoryWithUnit: String
        get() = showValueWithUnit(totalMemory)

    /** RAM size available for applications in bytes */
    val availableMemory: Long
        get() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                return memInfo.availMem
            } else {
                // TODO: use /proc/meminfo
                return 0L
            }
        }

    val availableMemoryWithUnit: String
        get() = showValueWithUnit(availableMemory)

    /** total internal storage size in bytes */
    val totalStorage: Long
        get() {
            val stat = StatFs(Environment.getExternalStorageDirectory().getPath())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                return stat.totalBytes
            }
            return stat.blockCount.toLong() * stat.blockSize.toLong()
        }

    val totalStorageWithUnit: String
        get() = showValueWithUnit(totalStorage)

    /** internal storage size available for applications in bytes */
    val availableStorage: Long
        get() {
            val stat = StatFs(Environment.getExternalStorageDirectory().getPath())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                return stat.availableBytes
            }
            return stat.availableBlocks.toLong() * stat.blockSize.toLong()
        }

    val availableStorageWithUnit: String
        get() = showValueWithUnit(availableStorage)

    fun showValueWithUnit(value: Long): String {
        // TODO: handle more units
        return "${value / 1024L / 1024L} MB"
    }
}
