package io.github.tonyguyot.systeminfo

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
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
        systemAndroidVersion.text = version
        systemCodeName.text = system.codeName
        systemSdkValue.text = sdk
        systemOsVersion.text = system.osVersion
    }

    fun initScreenView() {
        val screen = Screen(windowManager)
        screenResolution.setText(screen.dimensionsInPixels)
        screenDensity.setText(screen.fullDensityInfo)
        screenFontScaling.setText(screen.fontScaling)
    }

    fun initHardwareView() {
        val hw = Hardware(applicationContext)
        hardwareTitle.text = hw.fullProductName
        val product = resources.getString(R.string.hardware_brand_product, hw.brand, hw.product)
        hardwareModel.text = product
        if (hw.totalMemory > 0L) {
            val memory = resources.getString(R.string.hardware_show_size, hw.availableMemoryWithUnit,
                    hw.totalMemoryWithUnit)
            hardwareRamSize.visibility = View.VISIBLE
            hardwareRamSize.text = memory
        } else {
            hardwareRamSize.visibility = View.GONE
        }
        val storage = resources.getString(R.string.hardware_show_size, hw.availableStorageWithUnit,
                hw.totalStorageWithUnit)
        hardwareStorageSize.text = storage
    }
}
