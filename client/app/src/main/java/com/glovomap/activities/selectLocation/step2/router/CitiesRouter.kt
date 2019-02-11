package com.glovomap.activities.selectLocation.step2.router

import com.glovomap.sia.rest.request.City

interface CitiesRouter {

    fun openMainView(city: City)
}