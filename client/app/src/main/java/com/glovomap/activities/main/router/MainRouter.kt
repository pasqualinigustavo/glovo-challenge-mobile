package com.glovomap.activities.main.router

import com.glovomap.sia.rest.request.City

interface MainRouter {

    fun showMapView(city: City?)
}