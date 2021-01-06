package edu.uoc.pac4.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import edu.uoc.pac4.data.oauth.OAuthTokensResponse
import kotlinx.coroutines.launch


class OAuthViewModel(
        private val repository: AuthenticationRepository
) : ViewModel() {

    // Live Data
    val liveData = MutableLiveData<OAuthTokensResponse>()

   /* fun getOAuthTokens(): MutableLiveData<OAuthTokensResponse> {
        return oAuthTokensResponse
    }*/

    private val _response = MutableLiveData<Boolean?>()
    val response: LiveData<Boolean?>
        get() = _response

    //1
    fun getOAuthTokens(authorizationCode: String) {
        //2
        //val liveData = MutableLiveData<OAuthTokensResponse>()
        viewModelScope.launch {
            val oAuthTokens = repository.login(authorizationCode)
            //liveData.value = repository.login(authorizationCode)

            oAuthTokens?.let { response ->
                Log.d("OAuthViewModel", "Got Access token ${response.accessToken}")
                repository.saveAccessToken(response.accessToken)
                response.refreshToken?.let {
                    repository.saveRefreshToken(it)
                }
                _response.value = true

            } ?: run {
                // Failure :(
                _response.value = false
            }
            Log.d("OAuthViewModel", "onAuthorizationCodeRetrieved ${_response.value}")
            //3
            liveData.postValue(oAuthTokens)
        }
        //4
        //return liveData
    }

    private fun updateToken() {
        //Log.d(TAG, "Got Access token ${response.accessToken}")

    }







    /*
    OAuthTokensResponse
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String? = null,
    @SerialName("expires_in") val expiresInSeconds: Int? = null,
    @SerialName("token_type") val tokenType: String? = null,
    @SerialName("scope") val scopes: List<String>? = null,
    */      
            
    //fun getTokens(authorizationCode: String): OAuthTokensResponse?
    /*
    fun getTokens(authorizationCode: String) {
        // Get Availability from Repository and post result
        viewModelScope.launch {

            oAuthTokensResponse.postValue(repository.login(authorizationCode))
        }
    }*/

}
/*
private fun <T> MutableLiveData<T>.postValue(login: Boolean) {

}
private fun <T> MutableLiveData<T>.mutation(actions: (MutableLiveData<T>) -> Unit) {
    actions(this)
    this.value = this.value
}
*/
