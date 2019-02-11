package com.glovomap.activities.launcher.router

import com.glovomap.main.activity.router.LauncherNavigator

class LauncherActivityRouter(private val navigator: LauncherNavigator) : LauncherRouter {
    override fun showSelectCitiesView() {
        navigator.showSelectCitiesView()
    }

    override fun showGPSEnableView() {
        navigator.showGPSEnableView()
    }

    override fun showMainView() {
        navigator.showMainView()
    }
}