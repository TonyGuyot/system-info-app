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
                mainTitle.setText(R.string.title_system)
                displayView(system = true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_screen -> {
                mainTitle.setText(R.string.title_screen)
                displayView(screen = true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_hardware -> {
                mainTitle.setText(R.string.title_hardware)
                displayView(hardware = true)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun displayView(system: Boolean = false, screen: Boolean = false, hardware: Boolean = false) {
        systemView.visibility = if (system) View.VISIBLE else View.GONE
        screenView.visibility = if (screen) View.VISIBLE else View.GONE
        hardwareView.visibility = if (hardware) View.VISIBLE else View.GONE
    }
}
