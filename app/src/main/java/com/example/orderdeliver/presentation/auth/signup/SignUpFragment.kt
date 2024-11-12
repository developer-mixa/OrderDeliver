package com.example.orderdeliver.presentation.auth.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentSignUpBinding
import com.example.orderdeliver.presentation.views.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding: FragmentSignUpBinding by viewBinding()
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListeners()
    }

    private fun onClickListeners() = with(binding){
        signUpButton.setOnClickListener {
            viewModel.signUp(
                usernameEditText.text.toString(),
                passwordEditText.text.toString(),
                repeatPasswordEditText.text.toString(),
                "+79189059308",
                emailEditText.text.toString()
            )
        }
    }

    companion object{
        @JvmStatic
        fun instance() : SignUpFragment = SignUpFragment()
    }

}