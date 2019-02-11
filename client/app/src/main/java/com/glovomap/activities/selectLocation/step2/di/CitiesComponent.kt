package com.glovomap.activities.selectLocation.step2.di

import com.glovomap.activities.selectLocation.step2.CitiesFragment
import com.glovomap.activities.selectLocation.di.SelectLocationComponent
import com.glovomap.di.PerFragment
import dagger.Component

@PerFragment
@Component(
    dependencies = arrayOf(SelectLocationComponent::class),
    modules = arrayOf(CitiesModule::class)
)
interface CitiesComponent {

    fun inject(target: CitiesFragment)
}
