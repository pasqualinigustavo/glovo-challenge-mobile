package com.glovomap.fragments.map.di

import com.glovomap.di.PerFragment
import com.glovomap.fragments.map.MapInteractor
import com.glovomap.fragments.map.MapPresenter
import com.glovomap.fragments.map.router.MapFragmentRouter
import com.glovomap.fragments.map.router.MapRouter
import com.glovomap.main.activity.router.MapFragmentNavigator
import com.glovomap.rest.ApiComm
import dagger.Module
import dagger.Provides

@Module
class MapFragmentModule {

    @Provides
    @PerFragment
    fun interactor(apiComm: ApiComm): MapInteractor {
        return MapInteractor(apiComm)
    }

    @Provides
    @PerFragment
    fun navigator() = MapFragmentNavigator()

    @Provides
    @PerFragment
    fun router(navigator: MapFragmentNavigator): MapRouter = MapFragmentRouter(navigator)

    @Provides
    @PerFragment
    fun presenter(router: MapRouter, interactor: MapInteractor) =
        MapPresenter(interactor, router)
}