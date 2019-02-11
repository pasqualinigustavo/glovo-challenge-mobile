package com.glovomap.fragments.map.router

import com.glovomap.main.activity.router.MainNavigator

class MapFragmentRouter(private val navigator: MainNavigator) : MapRouter {

    override fun showSelectCitiesView() {
        navigator.showSelectCitiesView()
    }

}