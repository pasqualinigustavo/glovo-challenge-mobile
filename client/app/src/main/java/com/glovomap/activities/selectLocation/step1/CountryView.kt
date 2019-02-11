package com.glovomap.activities.selectLocation.step1

import com.glovomap.sia.rest.request.Country

interface CountryView {
    fun showCountries(countries: List<Country>)
    fun showError(message: String)
}