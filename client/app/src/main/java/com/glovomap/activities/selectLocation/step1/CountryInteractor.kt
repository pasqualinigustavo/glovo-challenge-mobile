package com.glovomap.activities.selectLocation.step1

import com.glovomap.rest.ApiComm

class CountryInteractor(private val apiComm: ApiComm) {

    private companion object {
        val TAG: String = CountryInteractor::class.java.simpleName
    }

    fun countryList() = apiComm.searchEndPoint().getCountries()

}