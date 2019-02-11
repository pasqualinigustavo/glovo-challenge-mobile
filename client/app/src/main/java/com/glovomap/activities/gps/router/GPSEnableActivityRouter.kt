package com.glovomap.activities.gps.router

class GPSEnableActivityRouter(private val navigator: GPSEnableNavigator) : GPSEnableRouter {
    override fun showSelectCitiesView() {
        navigator.showSelectCitiesView()
    }

    override fun showMainView() {
        navigator.showMainView()
    }
}