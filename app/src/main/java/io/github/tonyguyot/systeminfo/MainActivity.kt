package io.github.tonyguyot.systeminfo

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_system -> {
                message.setText(R.string.title_system)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_screen -> {
                message.setText(R.string.title_screen)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_hardware -> {
                message.setText(R.string.title_hardware)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
