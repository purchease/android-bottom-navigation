package com.purchease.example.modules.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.purchease.example.R
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : androidx.fragment.app.Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_privacy_settings.setOnClickListener {
            findNavController().navigate(R.id.action_privacy_settings)
        }

        btn_profile_settings.setOnClickListener {
            findNavController().navigate(R.id.action_profile_settings)
        }
    }
}
