package com.example.orderdeliver.presentation.plugins.plugins

import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.orderdeliver.R
import com.example.orderdeliver.presentation.activities.MainActivity
import com.example.orderdeliver.presentation.auth.auth.AuthFragment
import com.example.orderdeliver.presentation.plugins.core.BaseScreen
import com.example.orderdeliver.presentation.plugins.core.Event
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigatorPlugin @Inject constructor() : ActivityPlugin() {

    private val _result = MutableLiveData<Event<Any>>()
    val result: LiveData<Event<Any>> = _result

    fun launch(screen: BaseScreen, addToBackStack: Boolean = false, aboveAll: Boolean = false) = whenActivityActive{
        it as MainActivity

        if (!aboveAll)launchFragment(it, screen, addToBackStack)
        else launchFragment(it, screen, addToBackStack, R.id.fragmentMainContainer)
    }

    fun launchAuthFragment(){
        launch(AuthFragment.Screen(), addToBackStack = true, aboveAll = true)
    }

    fun goBack(result: Any? = null) = whenActivityActive{
        if (result != null){
            _result.value = Event(result)
        }
        it.onBackPressedDispatcher.onBackPressed()
    }

    fun launchFragment(activity: MainActivity, screen: BaseScreen, addToBackStack: Boolean = false, @IdRes idFragment: Int = R.id.fragmentContainer){
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to screen)
        val transaction = activity.supportFragmentManager.beginTransaction()

        if (addToBackStack) {
            transaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right);
            transaction.addToBackStack(null)
        }

        transaction.replace(idFragment, fragment).commit()

    }


    private companion object{
        const val ARG_SCREEN = "SCREEN"
    }

}