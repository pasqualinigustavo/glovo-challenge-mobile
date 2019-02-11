package com.glovomap.activities.selectLocation

import android.util.Log
import com.glovomap.R
import com.glovomap.activities.selectLocation.router.SelectLocationRouter
import com.glovomap.activities.selectLocation.step1.router.CountryRouter
import com.glovomap.app.GlovoApplication
import com.glovomap.sia.rest.request.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SelectLocationPresenter(private val router: SelectLocationRouter) {

    companion object {
        val TAG = SelectLocationPresenter::class.java.simpleName
    }

    var mView: SelectLocationView? = null

    fun bindView(view: SelectLocationView) {
        this.mView = view
    }

    fun unbindView() {
        this.mView = null
    }

    fun openSelectCountryView() {
        router.openSelectCountryView()
    }
}