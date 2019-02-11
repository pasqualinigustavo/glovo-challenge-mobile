package com.glovomap.fragments.map.router

import com.glovomap.main.activity.router.MapFragmentNavigator

class MapFragmentRouter(private val navigator: MapFragmentNavigator) : MapRouter {

    override fun showSelectLocationView() {
        navigator.showSelectLocationView()
    }

}