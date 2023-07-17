package com.example.navigation

import androidx.appcompat.app.AppCompatActivity

typealias MainActivityAction = (AppCompatActivity) -> Unit

class MainActivityActions {

    var mainActivity: AppCompatActivity? = null
        set(activity) {
            field = activity
            if (activity != null){
                actions.forEach { action-> action(activity) }
                actions.clear()
            }
        }

    private val actions = mutableListOf<MainActivityAction>()

    operator fun invoke(action: MainActivityAction){
        val activity = this.mainActivity
        if (activity == null){
            actions += action
        } else {
            action(activity)
        }
    }

    fun clear(){
        actions.clear()
    }

}