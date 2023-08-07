package com.example.orderdeliver.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navigation.BaseFragment
import com.example.navigation.BaseScreen
import com.example.navigation.Navigator

class ViewModelFactory(
    private val screen: BaseScreen,
    private val fragment: BaseFragment
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val hostActivity = fragment.requireActivity()
        val application = hostActivity.application
        val navigatorProvider = ViewModelProvider(hostActivity, ViewModelProvider.AndroidViewModelFactory(application))
        val navigator = navigatorProvider[MainNavigator::class.java]

        val constructor = modelClass.getConstructor(Navigator::class.java, screen::class.java)
        return constructor.newInstance(navigator,screen)
    }

}

fun Fragment.getMainNavigator(): MainNavigator {
    val hostActivity = requireActivity()
    val application = hostActivity.application
    val navigatorProvider = ViewModelProvider(hostActivity, ViewModelProvider.AndroidViewModelFactory(application))
    return navigatorProvider[MainNavigator::class.java]
}
fun Fragment.getBaseScreen() : BaseScreen{
    return requireArguments().getSerializable(ARG_SCREEN) as BaseScreen
}

inline fun <reified VM: ViewModel> BaseFragment.screenViewModel() = viewModels<VM>{
    val screen = requireArguments().getSerializable(ARG_SCREEN) as BaseScreen
    ViewModelFactory(screen,this)
}
