package com.example.orderdeliver

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.core.R

fun showLog(message: String, tag: String = "MyLog"){
    Log.d(tag,message)
}

@SuppressLint("CommitTransaction")
fun AppCompatActivity.replaceFragment(fragment: Fragment, @IdRes containerId: Int = com.example.orderdeliver.R.id.fragmentContainer){
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(containerId, fragment)
    transaction.commit()
}

@SuppressLint("CommitTransaction")
fun Fragment.replaceFragment(fragment: Fragment, @IdRes containerId: Int= com.example.orderdeliver.R.id.fragmentContainer){
    val transaction = parentFragmentManager.beginTransaction()
    transaction.replace(containerId, fragment)
    transaction.commit()
}