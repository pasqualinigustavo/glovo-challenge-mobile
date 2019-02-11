package com.glovomap.di.components

import android.content.Context
import com.glovomap.di.modules.ApiModule
import com.glovomap.di.modules.ApplicationDIModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationDIModule::class, ApiModule::class])
interface ApplicationComponent {

    fun context(): Context

    fun inject(holder: ComponentHolder)
}
