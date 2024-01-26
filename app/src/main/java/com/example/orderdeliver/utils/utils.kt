package com.example.orderdeliver.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

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

fun Fragment.collectFlow(block: suspend () -> Unit){
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}

fun <T> Flow<T>.simpleScan(count: Int): Flow<List<T?>> {
    val items = List<T?>(count) { null }
    return this.scan(items) {previous, value -> previous.drop(1) + value}
}


fun Fragment.getVerticalLayoutManager(): LinearLayoutManager{
    return LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
}

fun Fragment.getHorizontalLayoutManager(): LinearLayoutManager{
    return LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
}