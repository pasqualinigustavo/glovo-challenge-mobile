package com.glovomap.di.modules

import com.glovomap.rest.ApiComm
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun apiComm() = ApiComm.getInstance()
}