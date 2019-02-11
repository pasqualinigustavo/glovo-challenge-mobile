package com.glovomap.activities.selectLocation.step1.di

import com.glovomap.activities.selectLocation.router.SelectLocationNavigator
import com.glovomap.activities.selectLocation.step1.CountryInteractor
import com.glovomap.activities.selectLocation.step1.CountryPresenter
import com.glovomap.activities.selectLocation.step1.router.CountryActivityRouter
import com.glovomap.activities.selectLocation.step1.router.CountryRouter
import com.glovomap.di.PerFragment
import com.glovomap.rest.ApiComm
import dagger.Module
import dagger.Provides

@Module
class CountryModule {

    @Provides
    @PerFragment
    fun interactor(apiComm: ApiComm) =
        CountryInteractor(apiComm)

    @Provides
    @PerFragment
    fun presenter(router: CountryRouter, interactor: CountryInteractor) =
        CountryPresenter(router, interactor)

    @Provides
    @PerFragment
    fun router(navigator: SelectLocationNavigator): CountryRouter =
        CountryActivityRouter(navigator)
}