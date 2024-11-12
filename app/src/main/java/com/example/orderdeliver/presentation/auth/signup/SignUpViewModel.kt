package com.example.orderdeliver.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orderdeliver.data.base.BackendException
import com.example.orderdeliver.domain.requests.SignUpRequest
import com.example.orderdeliver.domain.usecases.SignUpUseCase
import com.example.orderdeliver.utils.showLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
): ViewModel() {

    fun signUp(username: String, password: String, rePassword: String, phone: String, email: String) = viewModelScope.launch{
        showLog("AUUUUUUUUUUUUUUU")
        try {
            val request = SignUpRequest(
                username,
                password,
                rePassword,
                null,
                null
            )
            signUpUseCase(request)
        } catch (e: BackendException){
            showLog(e.message.toString() + e.code)
        }
    }

}