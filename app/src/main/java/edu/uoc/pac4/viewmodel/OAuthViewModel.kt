package edu.uoc.pac4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uoc.pac4.R
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import edu.uoc.pac4.data.oauth.OAuthTokensResponse
import edu.uoc.pac4.utils.Resource

class OAuthViewModel(
        private val repository: AuthenticationRepository
) : ViewModel() {

    private val _oauthTokens = MutableLiveData<Resource<OAuthTokensResponse>>()
    val oauthTokens: LiveData<Resource<OAuthTokensResponse>>
        get() = _oauthTokens

    suspend fun getOAuthTokens(authorizationCode: String) {
        _oauthTokens.postValue(Resource.loading(null))

        repository.login(authorizationCode)?.let { response ->
            // Success :)
            repository.saveAccessToken(response.accessToken)
            response.refreshToken?.let {
                repository.saveRefreshToken(it)
            }
            _oauthTokens.postValue(Resource.success(response))
        } ?: run {
            // Failure :(
            _oauthTokens.postValue(Resource.error(R.string.error_oauth.toString(), null))
        }
    }
}