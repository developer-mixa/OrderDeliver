package com.example.orderdeliver.presentation.navigation

import android.app.Application
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
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

    override fun launch(screen: BaseScreen) = whenActivityActive{
        it as MainActivity
        launchFragment(it, screen)
    }


    override fun goBack(result: Any?) = whenActivityActive{
        if (result != null){
            _result.value = Event(result)
        }
        it.onBackPressed()
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
        if (addToBackStack)transaction.addToBackStack(null)
        transaction.replace(idFragment, fragment).commit()
    }

}