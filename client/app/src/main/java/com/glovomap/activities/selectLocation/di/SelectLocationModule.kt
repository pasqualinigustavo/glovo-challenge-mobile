package com.glovomap.activities.selectLocation.di

import com.glovomap.activities.selectLocation.SelectLocationActivity
import com.glovomap.activities.selectLocation.SelectLocationPresenter
import com.glovomap.activities.selectLocation.router.SelectLocationActivityRouter
import com.glovomap.activities.selectLocation.router.SelectLocationNavigator
import com.glovomap.activities.selectLocation.router.SelectLocationRouter
import com.glovomap.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class SelectLocationModule {

    @Provides
    @PerActivity
    fun router(navigator: SelectLocationNavigator): SelectLocationRouter = SelectLocationActivityRouter(navigator)

    @Provides
    @PerActivity
    fun navigator(activity: SelectLocationActivity) = SelectLocationNavigator(activity)

    @Provides
    @PerActivity
    fun presenter(router: SelectLocationRouter) = SelectLocationPresenter(router)

}