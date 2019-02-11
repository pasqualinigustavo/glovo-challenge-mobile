package com.glovomap.di.components

import com.glovomap.activities.main.MainActivity
import com.glovomap.activities.main.di.MainModule
import com.glovomap.di.PerActivity
import dagger.BindsInstance
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(MainModule::class))
interface MainComponent {

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        fun parent(parent: ApplicationComponent): Builder

        fun module(module: MainModule): Builder

        @BindsInstance
        fun target(target: MainActivity): Builder

        fun build(): MainComponent
    }
}
