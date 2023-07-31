package com.example.orderdeliver.presentation.navigation

import android.app.Application
import android.transition.Fade
import android.transition.Slide
import android.transition.TransitionSet
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.navigation.BaseScreen
import com.example.navigation.Event
import com.example.navigation.MainActivityActions
import com.example.navigation.Navigator
import com.example.orderdeliver.R
import com.example.orderdeliver.presentation.activities.MainActivity

const val ARG_SCREEN = "SCREEN"

class MainNavigator(
    application: Application
): AndroidViewModel(application), Navigator {

    val whenActivityActive = MainActivityActions()

    private val _result = MutableLiveData<Event<Any>>()
    val result: LiveData<Event<Any>> = _result

    override fun launch(screen: BaseScreen, addToBackStack: Boolean , aboveAll: Boolean) = whenActivityActive{
        it as MainActivity
        if (!aboveAll)launchFragment(it, screen, addToBackStack)
        else launchFragment(it, screen, addToBackStack, R.id.fragmentMainContainer)
    }


    override fun goBack(result: Any?) = whenActivityActive{
        if (result != null){
            _result.value = Event(result)
        }
        it.onBackPressedDispatcher.onBackPressed()
    }

    override fun onCleared() {
        super.onCleared()
        whenActivityActive.clear()
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(getApplication(), messageRes, Toast.LENGTH_LONG).show()
    }



    override fun toast(messageString: String) {
        Toast.makeText(getApplication(), messageString, Toast.LENGTH_LONG).show()
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

}