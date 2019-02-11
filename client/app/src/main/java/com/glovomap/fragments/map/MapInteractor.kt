package com.glovomap.fragments.map

import com.glovomap.rest.ApiComm

class MapInteractor(private val apiComm: ApiComm) {

    companion object {
        val TAG = MapInteractor::class.java.simpleName
    }

    fun cityInfo(code: String?) = apiComm.searchEndPoint().getCity(code)

    fun cityList() = apiComm.searchEndPoint().getCities()
}