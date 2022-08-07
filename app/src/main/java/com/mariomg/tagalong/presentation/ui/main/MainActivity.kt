package com.mariomg.tagalong.presentation.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.NavHostFragment
import com.mariomg.tagalong.R
import com.mariomg.tagalong.presentation.BUNDLE_KEY_URI
import com.mariomg.tagalong.presentation.ui.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ViewModelStoreOwner {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (isIntentFromCCT(intent)) {
            findExistingLoginFragment()?.let { loginFragment ->
                loginFragment.arguments = bundleOf(BUNDLE_KEY_URI to intent!!.data)
                showFragment(loginFragment)
            }
        }
    }

    private fun isIntentFromCCT(intent: Intent?): Boolean {
        return intent != null && intent.action == Intent.ACTION_VIEW
    }

    private fun findExistingLoginFragment(): LoginFragment? {
        val navHostFragment = supportFragmentManager.fragments
            .filterIsInstance<NavHostFragment>().firstOrNull()
        return navHostFragment?.childFragmentManager?.fragments
            ?.filterIsInstance<LoginFragment>()?.firstOrNull()
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentTransaction = fragment.parentFragmentManager.beginTransaction()
        fragmentTransaction.show(fragment).commit()
    }
}