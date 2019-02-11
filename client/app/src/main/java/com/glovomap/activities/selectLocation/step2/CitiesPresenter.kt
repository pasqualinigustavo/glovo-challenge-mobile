package com.glovomap.activities.selectLocation.step2

import android.util.Log
import com.glovomap.R
import com.glovomap.activities.selectLocation.step2.router.CitiesRouter
import com.glovomap.app.GlovoApplication
import com.glovomap.sia.rest.request.City
import com.glovomap.sia.rest.request.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CitiesPresenter(private val router: CitiesRouter, private val interactor: CitiesInteractor) {

    companion object {
        val TAG = CitiesPresenter::class.java.simpleName
    }

    var mView: CitiesView? = null

    fun bindView(view: CitiesView) {
        this.mView = view
    }

    fun unbindView() {
        this.mView = null
    }

    fun getCities(country: Country) {
        Log.d(TAG, "getCities")
        interactor.cityList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                if (response != null) {
                    filterCities(country, response)
                } else if (response == null) {
                    mView?.showError(GlovoApplication.getAppContext().getString(R.string.error_communication))
                }
            }, { error ->
                mView?.showError(GlovoApplication.getAppContext().getString(R.string.error_communication))
            })
    }

    fun openMainView(city: City) {
        router.openMainView(city)
    }

    private fun filterCities(country: Country, citiesOrg: List<City>) {
        var cities = ArrayList<City>()
        for (item in citiesOrg) {
            if (item.country_code == country.code)
                cities.add(item)
        }
        mView?.showCities(cities)
    }

}