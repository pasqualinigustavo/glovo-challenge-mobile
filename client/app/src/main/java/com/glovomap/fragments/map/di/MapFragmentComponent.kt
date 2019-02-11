package com.glovomap.fragments.map.di

import com.glovomap.di.PerFragment
import com.glovomap.di.components.MainComponent
import com.glovomap.fragments.map.MapFragment
import dagger.Component

@PerFragment
@Component(modules = arrayOf(MapFragmentModule::class), dependencies = arrayOf(MainComponent::class))
interface MapFragmentComponent {

    fun inject(target: MapFragment)
}