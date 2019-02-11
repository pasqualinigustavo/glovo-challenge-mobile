package com.glovomap.activities.main.di

import com.glovomap.activities.main.MainActivity
import com.glovomap.activities.main.MainPresenter
import com.glovomap.activities.main.router.MainActivityRouter
import com.glovomap.activities.main.router.MainRouter
import com.glovomap.di.PerActivity
import com.glovomap.main.activity.router.MainNavigator
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    @PerActivity
    fun router(navigator: MainNavigator): MainRouter = MainActivityRouter(navigator)

    @Provides
    @PerActivity
    fun navigator(activity: MainActivity) = MainNavigator(activity)

    @Provides
    @PerActivity
    fun presenter(router: MainRouter) =
            MainPresenter(router)
}