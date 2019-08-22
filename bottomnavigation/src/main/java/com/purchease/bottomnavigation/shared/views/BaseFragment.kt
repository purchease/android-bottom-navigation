package com.purchease.bottomnavigation.shared.views


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp

class BaseFragment : Fragment() {

    private val defaultInt = -1
    private var layoutRes: Int = -1
    private var toolbarId: Int = -1
    private var navHostId: Int = -1
    private var navGraphId: Int = -1
    private var bundle: Bundle? = null
    private var appBarConfig: AppBarConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutRes = it.getInt(KEY_LAYOUT)
            toolbarId = it.getInt(KEY_TOOLBAR)
            navHostId = it.getInt(KEY_NAV_HOST)
            navGraphId = it.getInt(KEY_NAV_GRAPH)
            bundle = it.getBundle(KEY_BUNDLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return if (layoutRes == defaultInt) null
        else inflater.inflate(layoutRes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val navController = requireActivity().findNavController(navHostId)
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(navGraphId)
        navController.setGraph(graph, bundle)
    }

    override fun onStart() {
        super.onStart()
        // return early if no arguments were parsed
        if (toolbarId == defaultInt || navHostId == defaultInt) return

//         setup navigation with toolbar
        val toolbar = requireActivity().findViewById<Toolbar>(toolbarId)
        val navController = requireActivity().findNavController(navHostId)

        appBarConfig = AppBarConfiguration(setOf(navController.graph.startDestination))
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfig!!)
    }

    fun onBackPressed(): Boolean {
        val navController = requireActivity()
                .findNavController(navHostId)
        return if (appBarConfig != null)
            navController.navigateUp(appBarConfig!!)
        else
            navController.navigateUp()
    }


    fun popToRoot() {
        val navController = requireActivity().findNavController(navHostId)
        navController.popBackStack(navController.graph.startDestination, false)
    }

    fun handleDeepLink(intent: Intent) = requireActivity().findNavController(navHostId).handleDeepLink(intent)

    companion object {

        private const val KEY_LAYOUT = "layout_key"
        private const val KEY_TOOLBAR = "toolbar_key"
        private const val KEY_NAV_HOST = "nav_host_key"
        private const val KEY_NAV_GRAPH = "nav_graph_key"
        private const val KEY_BUNDLE = "bundle_key"

        fun newInstance(layoutRes: Int, navHostId: Int, navGraphId: Int, bundle: Bundle? = null, toolbarId: Int = -1) = BaseFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_LAYOUT, layoutRes)
                putInt(KEY_TOOLBAR, toolbarId)
                putInt(KEY_NAV_HOST, navHostId)
                putInt(KEY_NAV_GRAPH, navGraphId)
                putBundle(KEY_BUNDLE, bundle)
            }
        }
    }
}