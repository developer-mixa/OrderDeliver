package com.example.orderdeliver.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager

fun showLog(message: String, tag: String = "MyLog"){
    Log.d(tag,message)
}

fun <T>MutableLiveData<T>.share(): LiveData<T>{
    return this
}

fun <T>ArrayList<T>.setList(newList: List<T>){
    this.clear()
    this.addAll(newList)
}

fun Fragment.getVerticalLayoutManager(): LinearLayoutManager{
    return LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
}

fun Fragment.getHorizontalLayoutManager(): LinearLayoutManager{
    return LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
}