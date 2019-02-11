package com.glovomap.activities.selectLocation.step1.di

import com.glovomap.activities.selectLocation.di.SelectLocationComponent
import com.glovomap.activities.selectLocation.step1.CountryFragment
import com.glovomap.di.PerFragment
import dagger.Component

@PerFragment
@Component(
    dependencies = arrayOf(SelectLocationComponent::class),
    modules = arrayOf(CountryModule::class)
)
interface CountryComponent {

    fun inject(target: CountryFragment)
}
