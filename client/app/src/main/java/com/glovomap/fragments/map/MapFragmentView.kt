package com.glovomap.fragments.map

import com.glovomap.sia.rest.request.City

interface MapFragmentView {
    fun showCity(cityInfo: List<City>)
    fun showError(message: String)
    fun showErrorLocation(message: String)
    fun readCity(city: City)
}