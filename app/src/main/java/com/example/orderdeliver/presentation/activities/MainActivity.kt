package com.example.orderdeliver.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.orderdeliver.presentation.plugins.core.BaseFragment
import com.example.orderdeliver.BuildConfig
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.ActivityMainBinding
import com.example.orderdeliver.presentation.main.MainFragment
import com.example.orderdeliver.presentation.plugins.plugins.ActivityPlugin
import com.example.orderdeliver.presentation.plugins.plugins.NavigatorPlugin
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var activityPlugins: Set<@JvmSuppressWildcards ActivityPlugin>
    @Inject lateinit var navigator: NavigatorPlugin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(BuildConfig.YANDEX_API_KEY)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            navigator.launchFragment(this, MainFragment.Screen(), idFragment = R.id.fragmentMainContainer)
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks,false)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
    }

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks(){
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            val result = navigator.result.value ?: return
            if (f is BaseFragment){
                f.viewModel.onResult(result)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        activityPlugins.forEach {
            it.onResume(this)
        }
    }

    override fun onPause() {
        super.onPause()
        activityPlugins.forEach { it.onPause() }
    }

}