package edu.uoc.pac4.ui.login.oauth

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.uoc.pac4.R
import edu.uoc.pac4.data.network.Endpoints
import edu.uoc.pac4.data.oauth.OAuthConstants
import edu.uoc.pac4.viewmodel.OAuthViewModel
import kotlinx.android.synthetic.main.activity_oauth.*
import org.koin.android.viewmodel.ext.android.viewModel

class OAuthActivity : AppCompatActivity() {

    private val TAG = "StreamsActivity"
    private val oAuthViewModel: OAuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oauth)
        launchOAuthAuthorization()
    }

    private fun buildOAuthUri(): Uri {
        return Uri.parse(Endpoints.authorizationUrl)
            .buildUpon()
            .appendQueryParameter("client_id", OAuthConstants.clientID)
            .appendQueryParameter("redirect_uri", OAuthConstants.redirectUri)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("scope", OAuthConstants.scopes.joinToString(separator = " "))
            .appendQueryParameter("state", OAuthConstants.uniqueState)
            .build()
    }

    private fun launchOAuthAuthorization() {
        //  Create URI
        val uri = buildOAuthUri()

        // Set webView Redirect Listener
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
            ): Boolean {
                request?.let {
                    // Check if this url is our OAuth redirect, otherwise ignore it
                    if (request.url.toString().startsWith(OAuthConstants.redirectUri)) {
                        // To prevent CSRF attacks, check that we got the same state value we sent, otherwise ignore it
                        val responseState = request.url.getQueryParameter("state")
                        if (responseState == OAuthConstants.uniqueState) {
                            // This is our request, obtain the code!
                            request.url.getQueryParameter("code")?.let { code ->
                                // Got it!
                                Log.d("OAuth", "Here is the authorization code! $code")
                                onAuthorizationCodeRetrieved(code)
                                // Hide WebView
                                webView.visibility = View.GONE
                            } ?: run {
                                // User cancelled the login flow
                                // Handle error
                                Toast.makeText(
                                        this@OAuthActivity,
                                        getString(R.string.error_oauth),
                                        Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        // Load OAuth Uri
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(uri.toString())
    }

    // Call this method after obtaining the authorization code
    // on the WebView to obtain the tokens
    private fun onAuthorizationCodeRetrieved(authorizationCode: String) {
        // Show Loading Indicator
        Log.d(TAG, "onAuthorizationCodeRetrieved")
        progressBar.visibility = View.VISIBLE

        oAuthViewModel.getOAuthTokens(authorizationCode)
        Log.d(TAG, "oAuthViewModel.getOAuthTokens(authorizationCode)")

        oAuthViewModel.response.observe(this) {response ->

            Log.d(TAG, "Got response $response")

           /* if(response!!) {
                Log.d(TAG, "Got Access token")
            } else {
                Log.d(TAG, "Error Access token")
                // Show Error Message
                Toast.makeText(
                        this@OAuthActivity,
                        getString(R.string.error_oauth),
                        Toast.LENGTH_LONG
                ).show()
                // Restart Activity
                finish()
                startActivity(Intent(this@OAuthActivity, OAuthActivity::class.java))
            }

            // Hide Loading Indicator
            progressBar.visibility = View.GONE
            Log.d(TAG, " startActivity(Intent(")
            // Restart app to navigate to StreamsActivity
            startActivity(Intent(this@OAuthActivity, LaunchActivity::class.java))
            finish()*/
        }
    }

        /*
        // Create Twitch Service
        val service = TwitchApiService(Network.createHttpClient(this))
        // Launch new thread attached to this Activity.
        // If the Activity is closed, this Thread will be cancelled
        lifecycleScope.launch {

            // Launch get Tokens Request
            service.getTokens(authorizationCode)?.let { response ->
                // Success :)

                Log.d(TAG, "Got Access token ${response.accessToken}")

                // Save access token and refresh token using the SessionManager class
                val sessionManager = SessionManager(this@OAuthActivity)
                sessionManager.saveAccessToken(response.accessToken)
                response.refreshToken?.let {
                    sessionManager.saveRefreshToken(it)
                }
            } ?: run {
                // Failure :(

                // Show Error Message
                Toast.makeText(
                    this@OAuthActivity,
                    getString(R.string.error_oauth),
                    Toast.LENGTH_LONG
                ).show()
                // Restart Activity
                finish()
                startActivity(Intent(this@OAuthActivity, OAuthActivity::class.java))
            }

            // Hide Loading Indicator
            progressBar.visibility = View.GONE

            // Restart app to navigate to StreamsActivity
            startActivity(Intent(this@OAuthActivity, LaunchActivity::class.java))
            finish()
        }
    }*/
}