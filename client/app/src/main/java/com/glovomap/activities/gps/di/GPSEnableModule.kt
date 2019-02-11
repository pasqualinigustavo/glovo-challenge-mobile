package com.glovomap.activities.gps.di

import com.glovomap.activities.gps.GPSEnableActivity
import com.glovomap.activities.gps.GPSEnablePresenter
import com.glovomap.activities.gps.router.GPSEnableNavigator
import com.glovomap.di.PerActivity
import com.glovomap.rest.ApiComm
import dagger.Module
import dagger.Provides

@Module
class GPSEnableModule {

    @Provides
    @PerActivity
    fun navigator(activity: GPSEnableActivity) = GPSEnableNavigator(activity)

    @Provides
    @PerActivity
    fun presenter(apiComm: ApiComm) = GPSEnablePresenter(apiComm)
}