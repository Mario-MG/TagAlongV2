package com.hfad.tagalong.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.ui.main.MainViewModel

abstract class BaseLoggedInFragment : Fragment() {

    protected val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.addLogoutObserver(viewLifecycleOwner, {
            findNavController().navigate(R.id.loginFragment)
        })
    }

}