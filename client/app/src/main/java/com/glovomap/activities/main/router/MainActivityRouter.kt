package com.glovomap.activities.main.router

import com.glovomap.main.activity.router.MainNavigator
import com.glovomap.sia.rest.request.City

class MainActivityRouter(private val navigator: MainNavigator) : MainRouter {

    override fun showMapView(city: City) {
        navigator.showMapView(city)
    }

}