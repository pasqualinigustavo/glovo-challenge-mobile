package com.glovomap.activities.selectLocation.router

import com.glovomap.activities.selectLocation.step1.CountryFragment

class SelectLocationActivityRouter(private val navigator: SelectLocationNavigator) : SelectLocationRouter {

    override fun openSelectCountryView() {
        navigator.switchContent(CountryFragment.getInstance(), false)
    }
}