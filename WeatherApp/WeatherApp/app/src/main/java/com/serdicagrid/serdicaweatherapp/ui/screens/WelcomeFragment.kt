// WelcomeFragment.kt
package com.serdicagrid.serdicaweatherapp.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.serdicagrid.serdicaweatherapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the welcome fragment layout
        val view = inflater.inflate(R.layout.welcome_screen_fragment_layout, container, false)

        // Automatically remove fragment after 3 seconds
        viewLifecycleOwner.lifecycleScope.launch {
            delay(3000)
            parentFragmentManager.beginTransaction().remove(this@WelcomeFragment).commit()
        }

        return view
    }
}
