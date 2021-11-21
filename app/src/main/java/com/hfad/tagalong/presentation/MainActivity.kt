package com.hfad.tagalong.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.ui.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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