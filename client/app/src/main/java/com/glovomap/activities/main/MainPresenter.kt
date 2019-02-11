package com.glovomap.activities.main

import com.glovomap.activities.main.router.MainRouter
import com.glovomap.sia.rest.request.City

class MainPresenter(private val router: MainRouter) {

    var mView: MainView? = null

    fun bindView(view: MainView) {
        this.mView = view
    }

    fun unbindView() {
        this.mView = null
    }

    fun showMapView(city: City?) {
        router.showMapView(city)
    }
}