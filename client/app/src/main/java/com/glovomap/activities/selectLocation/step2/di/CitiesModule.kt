package com.glovomap.activities.selectLocation.step2.di

import com.glovomap.activities.selectLocation.router.SelectLocationNavigator
import com.glovomap.activities.selectLocation.step2.CitiesInteractor
import com.glovomap.activities.selectLocation.step2.CitiesPresenter
import com.glovomap.activities.selectLocation.step2.router.CitiesActivityRouter
import com.glovomap.activities.selectLocation.step2.router.CitiesRouter
import com.glovomap.di.PerFragment
import com.glovomap.rest.ApiComm
import dagger.Module
import dagger.Provides

@Module
class CitiesModule {

    @Provides
    @PerFragment
    fun interactor(apiComm: ApiComm) =
        CitiesInteractor(apiComm)

    @Provides
    @PerFragment
    fun presenter(router: CitiesRouter, interactor: CitiesInteractor) =
        CitiesPresenter(router, interactor)

    @Provides
    @PerFragment
    fun router(navigator: SelectLocationNavigator): CitiesRouter =
        CitiesActivityRouter(navigator)
}