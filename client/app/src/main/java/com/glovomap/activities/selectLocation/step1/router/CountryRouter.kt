package com.glovomap.activities.selectLocation.step1.router

import com.glovomap.sia.rest.request.Country

interface CountryRouter {

    fun openSelectCityView(country: Country)
}