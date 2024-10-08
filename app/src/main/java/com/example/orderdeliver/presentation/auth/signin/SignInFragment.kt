package com.example.orderdeliver.presentation.auth.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.FragmentSignInBinding
import com.example.orderdeliver.presentation.views.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding: FragmentSignInBinding by viewBinding()
    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListeners()
    }

    private fun onClickListeners() = with(binding){
        signInButton.setOnClickListener {
            viewModel.signIn(usernameInput.text.toString(), passwordInput.text.toString())
        }
    }

    companion object{
        @JvmStatic
        fun instance() : SignInFragment = SignInFragment()
    }

}