package com.example.orderdeliver.di

import com.example.orderdeliver.presentation.plugins.plugins.ActivityPlugin
import com.example.orderdeliver.presentation.plugins.plugins.LocationPlugin
import com.example.orderdeliver.presentation.plugins.plugins.NavigatorPlugin
import com.example.orderdeliver.presentation.plugins.plugins.PermissionPlugin
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds

@Module
@InstallIn(SingletonComponent::class)
interface PluginsModule {

    @Multibinds
    fun bindActivityPlugins(): Set<ActivityPlugin>

    @Binds
    @IntoSet
    fun bindNavigatorPlugin(navigator: NavigatorPlugin) : ActivityPlugin

    @Binds
    @IntoSet
    fun bindPermissionsPlugin(permissionPlugin: PermissionPlugin) : ActivityPlugin

    @Binds
    @IntoSet
    fun bindLocationPlugin(locationPlugin: LocationPlugin) : ActivityPlugin

}