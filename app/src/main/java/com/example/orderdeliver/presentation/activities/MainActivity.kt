package com.example.orderdeliver.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.orderdeliver.R
import com.example.orderdeliver.databinding.ActivityMainBinding
import com.example.orderdeliver.presentation.main.MainFragment
import com.example.orderdeliver.presentation.navigation.MainNavigator
import com.example.orderdeliver.utils.Const
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val navigator by viewModels<MainNavigator>{ ViewModelProvider.AndroidViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(Const.YANDEX_KEY)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null){
            navigator.launchFragment(this, MainFragment.Screen(), idFragment = R.id.fragmentMainContainer)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        navigator.whenActivityActive.mainActivity = this
    }

    override fun onPause() {
        super.onPause()
        navigator.whenActivityActive.mainActivity = null
    }

}