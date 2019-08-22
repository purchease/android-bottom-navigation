package com.purchease.example.modules.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.purchease.example.R
import com.purchease.example.TestModel
import com.purchease.example.shared.utility.HandleNotifications
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar_home)
        toolbar.inflateMenu(R.menu.menu_home)
        toolbar.setOnMenuItemClickListener(this::onOptionsItemSelected)

        arguments?.let {
            val model = it.get("test") as TestModel
            Log.w("TEST", model.val1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_notify.setOnClickListener { HandleNotifications.showNotification(requireContext()) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val destination = when (item?.itemId) {
            R.id.search -> R.id.action_search
            else -> null
        }

        return if (destination != null) findNavController().navigate(destination).let { true }
        else super.onOptionsItemSelected(item)
    }

}
