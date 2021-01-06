package edu.uoc.pac4.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import edu.uoc.pac4.R
import edu.uoc.pac4.ui.login.LoginActivity
import edu.uoc.pac4.ui.streams.StreamsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class LaunchActivity : AppCompatActivity() {

    private val launchViewModel: LaunchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        checkUserSession()
        launchViewModel.getUserAvailability()
    }

    private fun checkUserSession() {
        launchViewModel.isUserAvailable.observe( this, Observer { isUserAvailable ->
            if (isUserAvailable) {
                // User is available, open Streams Activity
                startActivity(Intent(this, StreamsActivity::class.java))
            } else {
                // User not available, request Login
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        })
    }
}