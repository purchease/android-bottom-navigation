package com.purchease.bottomnavigation

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.purchease.bottomnavigation.shared.views.BaseFragment
import java.util.*

abstract class BottomNavigationActivity : AppCompatActivity(),
        ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemReselectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener {


    // overall back stack of containers
    private val backStack = Stack<Int>()

    // list of base destination containers
    abstract val fragments: List<BaseFragment>

    // map of navigation_id to container index
    abstract val indexToPage: Map<Int, Int>

    lateinit var viewPager: ViewPager
    lateinit var bottomNavigationView: BottomNavigationView

    fun initBottomNavigation(viewPager: ViewPager, bottomNavigationView: BottomNavigationView) {
        // setup main view pager
        this.viewPager = viewPager
        this.bottomNavigationView = bottomNavigationView

        viewPager.addOnPageChangeListener(this)
        viewPager.adapter = ViewPagerAdapter()
        viewPager.post(this::checkDeepLink)
        viewPager.offscreenPageLimit = fragments.size

        // set bottom nav
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.setOnNavigationItemReselectedListener(this)

        // initialize backStack with elements
        if (backStack.empty()) backStack.push(0)
    }

    /// BottomNavigationView ItemSelected Implementation
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val position = indexToPage.values.indexOf(item.itemId)
        if (viewPager.currentItem != position) setItem(position)
        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        val position = indexToPage.values.indexOf(item.itemId)
        val fragment = fragments[position]
        fragment.popToRoot()
    }

    override fun onBackPressed() {
        val fragment = fragments[viewPager.currentItem]
        val hadNestedFragments = fragment.onBackPressed()
        // if no fragments were popped
        if (!hadNestedFragments) {
            if (backStack.size > 1) {
                // remove current position from stack
                backStack.pop()
                // set the next item in stack as current
                viewPager.currentItem = backStack.peek()

            } else super.onBackPressed()
        }
    }

    /// OnPageSelected Listener Implementation
    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(page: Int) {
        val itemId = indexToPage[page] ?: R.id.home
        if (bottomNavigationView.selectedItemId != itemId) bottomNavigationView.selectedItemId = itemId
    }

    private fun setItem(position: Int) {
        viewPager.currentItem = position
        backStack.remove(position)
        backStack.push(position)
    }

    private fun checkDeepLink() {
        fragments.forEachIndexed { index, fragment ->
            val hasDeepLink = fragment.handleDeepLink(intent)
            if (hasDeepLink) setItem(index)
        }
    }

    inner class ViewPagerAdapter : FragmentPagerAdapter(supportFragmentManager) {

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

    }
}
