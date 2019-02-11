package com.glovomap.fragments.map

import com.glovomap.sia.rest.request.City

interface MapFragmentView {
    fun showCity(city: City)
    fun showError(message: String)
    fun showErrorLocation(message: String)
    fun readCity(city: City)
}