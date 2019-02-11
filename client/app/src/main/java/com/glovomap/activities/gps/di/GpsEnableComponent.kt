package com.glovomap.activities.gps.di

import dagger.BindsInstance
import dagger.Component
import com.glovomap.activities.gps.GPSEnableActivity
import com.glovomap.di.PerActivity
import com.glovomap.di.components.ApplicationComponent

@PerActivity
@Component(dependencies = [(ApplicationComponent::class)], modules = [(GPSEnableModule::class)])
interface GpsEnableComponent {

    fun inject(activity: GPSEnableActivity)

    @Component.Builder
    interface Builder {
        fun parent(parent: ApplicationComponent): Builder
        fun module(module: GPSEnableModule): Builder
        @BindsInstance
        fun target(target: GPSEnableActivity): Builder

        fun build(): GpsEnableComponent
    }
}
