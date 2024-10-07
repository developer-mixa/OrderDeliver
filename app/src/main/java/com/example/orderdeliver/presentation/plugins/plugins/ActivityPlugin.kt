package com.example.orderdeliver.presentation.plugins.plugins

import androidx.appcompat.app.AppCompatActivity
import com.example.orderdeliver.presentation.plugins.core.MainActivityActions

open class ActivityPlugin {

    val whenActivityActive: MainActivityActions = MainActivityActions()

    open fun onResume(activity: AppCompatActivity){
        whenActivityActive.mainActivity = activity
    }

    open fun onPause(){
        whenActivityActive.mainActivity = null
    }
}