package com.glovomap.activities.selectLocation.step2

import com.glovomap.sia.rest.request.City

interface CitiesView {
    fun showCities(cities: List<City>)
    fun showError(message: String)
}