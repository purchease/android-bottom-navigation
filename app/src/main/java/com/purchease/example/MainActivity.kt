package com.purchease.example

import android.os.Bundle
import com.purchease.bottomnavigation.BottomNavigationActivity
import com.purchease.bottomnavigation.shared.views.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BottomNavigationActivity() {

    override val fragments: List<BaseFragment> = listOf(
            BaseFragment.newInstance(R.layout.content_home_base, R.id.nav_host_home),
            BaseFragment.newInstance(R.layout.content_library_base, R.id.nav_host_library),
            BaseFragment.newInstance(R.layout.content_settings_base, R.id.nav_host_settings, R.id.toolbar_settings))

    override val indexToPage = mapOf(0 to R.id.home, 1 to R.id.library, 2 to R.id.settings)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigation(main_pager, bottom_nav)
    }
}