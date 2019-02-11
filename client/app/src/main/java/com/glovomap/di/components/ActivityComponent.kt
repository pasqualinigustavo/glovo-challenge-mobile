package com.glovomap.di.components

import android.app.Activity

import com.glovomap.di.PerActivity
import com.glovomap.di.modules.ActivityModule

import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun activity(): Activity
}
