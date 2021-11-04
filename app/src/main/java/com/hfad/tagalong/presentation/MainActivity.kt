package com.hfad.tagalong.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.hfad.tagalong.R
import com.hfad.tagalong.presentation.ui.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && intent.action == Intent.ACTION_VIEW && intent.data != null) {
            val navHostFragment = supportFragmentManager.fragments.find { fragment -> fragment is NavHostFragment }
            navHostFragment?.let { navHostFrag ->
                val loginFragment = navHostFrag.childFragmentManager.fragments.find { childFragment -> childFragment is LoginFragment }
                loginFragment?.let { loginFrag ->
                    val bundle = bundleOf("uri" to intent.data)
                    loginFrag.arguments = bundle
                    val fragmentTransaction = loginFrag.parentFragmentManager.beginTransaction()
                    fragmentTransaction.show(loginFrag).commit()
                }
            }
        }
    }
}