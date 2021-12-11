package com.hfad.tagalong.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.ui.main.MainViewModel

abstract class BaseLoggedInFragment : Fragment() {

    protected val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.addLogoutObserver(viewLifecycleOwner, {
            findNavController().navigate(
                R.id.loginFragment,
                null,
                NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_up)
//                    .setExitAnim(R.anim.slide_out_down)
//                    .setPopEnterAnim(R.anim.slide_in_down)
                    .setPopExitAnim(R.anim.slide_out_up)
                    .build()
            )
        })
    }

}