package com.example.orderdeliver.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orderdeliver.R
import com.example.orderdeliver.data.base.BackendException
import com.example.orderdeliver.domain.repositories.ProfileRepository
import com.example.orderdeliver.domain.usecases.ExitAccountUseCase
import com.example.orderdeliver.domain.usecases.GetUserTokenUseCase
import com.example.orderdeliver.presentation.plugins.plugins.NavigatorPlugin
import com.example.orderdeliver.presentation.plugins.plugins.ToastPlugin
import com.example.orderdeliver.utils.HttpStatus
import com.example.orderdeliver.utils.showLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val navigator: NavigatorPlugin,
    private val toasts: ToastPlugin,
    private val profileRepository: ProfileRepository,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val exitAccountUseCase: ExitAccountUseCase
) : ViewModel() {

    fun getCurrentUserData() = viewModelScope.launch {
        try {
            val successToken = getUserTokenUseCase()
            profileRepository.getMe(successToken)
        } catch (e: BackendException) {
            val errorMessage =
                if (e.code == HttpStatus.UNAUTHORIZED) R.string.not_authorized else R.string.server_error

            toasts.toast(errorMessage)
            navigator.launchAuthFragment()
        } catch (e: Exception) {
            toasts.toast(R.string.undefined_error)
            showLog(e.toString())
        }
    }

    fun exitFromProfile() {
        exitAccountUseCase()
        navigator.launchAuthFragment()
    }

}
