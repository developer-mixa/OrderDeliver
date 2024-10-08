package com.example.orderdeliver.presentation.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orderdeliver.domain.requests.SignInRequest
import com.example.orderdeliver.domain.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
): ViewModel() {

    fun signIn(username: String, password: String) = viewModelScope.launch {
        signInUseCase(SignInRequest(username, password))
    }

}