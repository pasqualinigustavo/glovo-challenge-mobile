package com.glovomap.activities.selectLocation.step2.router

import com.glovomap.activities.selectLocation.router.SelectLocationNavigator
import com.glovomap.sia.rest.request.City

class CitiesActivityRouter(private val navigator: SelectLocationNavigator) : CitiesRouter {

    override fun openMainView(city: City) {
        navigator.openMainView(city)
    }
}