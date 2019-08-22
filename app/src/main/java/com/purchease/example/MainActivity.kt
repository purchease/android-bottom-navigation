package com.purchease.example

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.core.os.bundleOf
import com.purchease.bottomnavigation.BottomNavigationActivity
import com.purchease.bottomnavigation.shared.views.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BottomNavigationActivity() {

    override val fragments: List<BaseFragment> = listOf(
            BaseFragment.newInstance(R.layout.content_home_base, R.id.nav_host_home, R.navigation.nav_graph_home, bundleOf("test" to TestModel())),
            BaseFragment.newInstance(R.layout.content_library_base, R.id.nav_host_library, R.navigation.nav_graph_library),
            BaseFragment.newInstance(R.layout.content_settings_base, R.id.nav_host_settings, R.navigation.nav_graph_settings, toolbarId = R.id.toolbar_settings))

    override val indexToPage = mapOf(0 to R.id.home, 1 to R.id.library, 2 to R.id.settings)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigation(main_pager, bottom_nav)
    }
}

class TestModel() : Parcelable {

    var val1: String? = "value 1"
    var val2 = listOf("test1", "test2", "test3")

    constructor(parcel: Parcel) : this() {
        val1 = parcel.readString()
        val2 = parcel.createStringArrayList()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(val1)
        parcel.writeStringList(val2)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestModel> {
        override fun createFromParcel(parcel: Parcel): TestModel {
            return TestModel(parcel)
        }

        override fun newArray(size: Int): Array<TestModel?> {
            return arrayOfNulls(size)
        }
    }
}
