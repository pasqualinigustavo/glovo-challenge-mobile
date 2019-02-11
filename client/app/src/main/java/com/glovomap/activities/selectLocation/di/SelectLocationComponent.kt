package com.glovomap.activities.selectLocation.di

import com.glovomap.activities.selectLocation.SelectLocationActivity
import com.glovomap.activities.selectLocation.router.SelectLocationNavigator
import com.glovomap.di.PerActivity
import com.glovomap.di.components.ApplicationComponent
import dagger.BindsInstance
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(SelectLocationModule::class))
interface SelectLocationComponent {

    fun inject(item: SelectLocationActivity)
    fun provideSelectLocationNavigator(): SelectLocationNavigator

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun target(activity: SelectLocationActivity): Builder

        fun module(module: SelectLocationModule): Builder
        fun parent(applicationComponent: ApplicationComponent): Builder
        fun build(): SelectLocationComponent
    }
}
