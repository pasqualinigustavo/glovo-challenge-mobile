package com.glovomap.di.modules

import android.content.Context
import com.glovomap.app.GlovoApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationDIModule(private val application: GlovoApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }
}