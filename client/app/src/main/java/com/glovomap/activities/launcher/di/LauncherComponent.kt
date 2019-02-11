package com.glovomap.activities.launcher.di

import com.glovomap.activities.launcher.LauncherActivity
import com.glovomap.di.PerActivity
import com.glovomap.di.components.ApplicationComponent
import dagger.BindsInstance
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(LauncherModule::class))
interface LauncherComponent {

    fun inject(activity: LauncherActivity)

    @Component.Builder
    interface Builder {
        fun parent(parent: ApplicationComponent): Builder
        fun module(module: LauncherModule): Builder
        @BindsInstance
        fun target(target: LauncherActivity): Builder

        fun build(): LauncherComponent
    }
}
