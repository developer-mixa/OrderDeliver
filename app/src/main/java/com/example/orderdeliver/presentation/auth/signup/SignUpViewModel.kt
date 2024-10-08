package com.example.orderdeliver.presentation.auth.signup

import androidx.lifecycle.ViewModel
import com.example.orderdeliver.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
): ViewModel() {

}