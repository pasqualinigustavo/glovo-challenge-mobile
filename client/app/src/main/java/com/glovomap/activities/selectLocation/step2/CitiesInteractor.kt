package com.glovomap.activities.selectLocation.step2

import com.glovomap.rest.ApiComm

class CitiesInteractor(private val apiComm: ApiComm) {

    private companion object {
        val TAG: String = CitiesInteractor::class.java.simpleName
    }

    fun cityList() = apiComm.searchEndPoint().getCities()

}