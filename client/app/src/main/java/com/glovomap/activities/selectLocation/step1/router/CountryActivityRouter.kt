package com.glovomap.activities.selectLocation.step1.router

import com.glovomap.activities.selectLocation.router.SelectLocationNavigator
import com.glovomap.activities.selectLocation.step2.CitiesFragment
import com.glovomap.sia.rest.request.City
import com.glovomap.sia.rest.request.Country

class CountryActivityRouter(private val navigator: SelectLocationNavigator) : CountryRouter {

    override fun openSelectCityView(country: Country) {
        navigator.switchContent(CitiesFragment.getInstance(country), false)
    }
}