package com.glovomap.activities.gps.di

import com.glovomap.activities.gps.GPSEnableActivity
import com.glovomap.activities.gps.GPSEnablePresenter
import com.glovomap.activities.gps.router.GPSEnableActivityRouter
import com.glovomap.activities.gps.router.GPSEnableNavigator
import com.glovomap.activities.gps.router.GPSEnableRouter
import com.glovomap.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class GPSEnableModule {

    @Provides
    @PerActivity
    fun navigator(activity: GPSEnableActivity) = GPSEnableNavigator(activity)

    @Provides
    @PerActivity
    fun router(navigator: GPSEnableNavigator): GPSEnableRouter = GPSEnableActivityRouter(navigator)

    @Provides
    @PerActivity
    fun presenter(router: GPSEnableRouter) = GPSEnablePresenter(router)
}