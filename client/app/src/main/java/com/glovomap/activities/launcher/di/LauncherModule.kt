package com.glovomap.activities.launcher.di

import com.glovomap.activities.launcher.LauncherActivity
import com.glovomap.activities.launcher.LauncherPresenter
import com.glovomap.activities.launcher.router.LauncherActivityRouter
import com.glovomap.activities.launcher.router.LauncherRouter
import com.glovomap.activities.main.MainActivity
import com.glovomap.activities.main.MainPresenter
import com.glovomap.activities.main.router.MainRouter
import com.glovomap.di.PerActivity
import com.glovomap.main.activity.router.LauncherNavigator
import com.glovomap.main.activity.router.MainNavigator
import dagger.Module
import dagger.Provides

@Module
class LauncherModule {

    @Provides
    @PerActivity
    fun router(navigator: LauncherNavigator): LauncherRouter = LauncherActivityRouter(navigator)

    @Provides
    @PerActivity
    fun navigator(activity: LauncherActivity) = LauncherNavigator(activity)

    @Provides
    @PerActivity
    fun presenter(router: LauncherRouter) =
            LauncherPresenter(router)
}