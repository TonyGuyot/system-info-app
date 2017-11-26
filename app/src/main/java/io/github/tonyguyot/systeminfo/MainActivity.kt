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

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.hardware.*
import kotlinx.android.synthetic.main.screen.*
import kotlinx.android.synthetic.main.system.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_system -> {
                displayView(system = true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_screen -> {
                displayView(screen = true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_hardware -> {
                displayView(hardware = true)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSystemView()
        initScreenView()
        initHardwareView()
        mainNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // recovering the instance state
        if (savedInstanceState != null) {
            val selectedIndex = savedInstanceState.getInt("index")
            when (selectedIndex) {
                R.id.navigation_system -> displayView(system = true)
                R.id.navigation_screen -> displayView(screen = true)
                R.id.navigation_hardware -> displayView(hardware = true)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("index", mainNavigation.selectedItemId)
    }

    fun displayView(system: Boolean = false, screen: Boolean = false, hardware: Boolean = false) {
        systemView.visibility = if (system) View.VISIBLE else View.GONE
        screenView.visibility = if (screen) View.VISIBLE else View.GONE
        hardwareView.visibility = if (hardware) View.VISIBLE else View.GONE
    }

    fun initSystemView() {
        val system = System()
        val version = resources.getString(R.string.system_android_version, system.release)
        val sdk = resources.getString(R.string.system_api_level, system.sdkVersion)
        val locale = resources.getString(R.string.system_locale, system.locale)
        systemAndroidVersion.text = version
        systemCodeName.text = system.codeName
        systemSdkValue.text = sdk
        systemOsVersion.text = system.osVersion
        systemLocale.text = locale
    }

    fun initScreenView() {
        val screen = Screen(windowManager, applicationContext)
        screenResolution.text = screen.dimensionsInPixels
        screenDensity.text = screen.fullDensityInfo
        screenSize.text = screen.size
        screenFontScaling.text = screen.fontScaling
        if (screen.isLandscape()) {
            screenOrientation.text = resources.getString(R.string.screen_orientation_landscape)
        } else if (screen.isPortrait()) {
            screenOrientation.text = resources.getString(R.string.screen_orientation_portrait)
        } else {
            screenOrientation.text = resources.getString(R.string.screen_orientation_undefined)
        }
    }

    fun initHardwareView() {
        val hw = Hardware(applicationContext)
        hardwareTitle.text = hw.fullProductName
        hardwareBrand.text = hw.brand.capitalize()
        hardwareProduct.text = hw.product
        if (hw.totalMemory > 0L) {
            val memory = resources.getString(R.string.hardware_show_size, hw.totalMemoryWithUnit,
                    hw.availableMemoryWithUnit)
            hardwareRamSize.visibility = View.VISIBLE
            hardwareRamSize.text = memory
        } else {
            hardwareRamSize.visibility = View.GONE
        }
        val storage = resources.getString(R.string.hardware_show_size, hw.totalStorageWithUnit,
                hw.availableStorageWithUnit)
        hardwareStorageSize.text = storage
    }

    ///////////////////
    //// MENU

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.itemId == R.id.action_about) {
            displayAboutBox()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun displayAboutBox() {
        val dlg = Dialog(this)
        dlg.setContentView(R.layout.dialog_about)
        dlg.setTitle(R.string.about_title)
        val btn = dlg.findViewById<View>(R.id.about_ok) as Button
        btn.setOnClickListener { dlg.dismiss() }
        dlg.show()
    }
}
