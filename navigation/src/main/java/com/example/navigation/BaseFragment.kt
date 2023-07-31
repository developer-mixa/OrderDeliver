package com.example.navigation

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel


abstract class BaseFragment(@LayoutRes layoutId: Int): Fragment(layoutId) {

    abstract val viewModel: BaseViewModel

}

