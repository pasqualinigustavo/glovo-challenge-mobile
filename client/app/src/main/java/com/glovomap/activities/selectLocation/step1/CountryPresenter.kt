package com.glovomap.activities.selectLocation.step1

import android.util.Log
import com.glovomap.R
import com.glovomap.activities.selectLocation.step1.router.CountryRouter
import com.glovomap.app.GlovoApplication
import com.glovomap.sia.rest.request.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CountryPresenter(private val router: CountryRouter, private val interactor: CountryInteractor) {

    companion object {
        val TAG = CountryPresenter::class.java.simpleName
    }

    var mView: CountryView? = null

    fun bindView(view: CountryView) {
        this.mView = view
    }

    fun unbindView() {
        this.mView = null
    }

    fun openSelectCityView(country: Country) {
        router.openSelectCityView(country)
    }

    fun getCountries() {
        Log.d(TAG, "getCities")
        interactor.countryList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                if (response != null) {
                    mView?.showCountries(response)
                } else if (response == null) {
                    mView?.showError(GlovoApplication.getAppContext().getString(R.string.error_communication))
                }
            }, { error ->
                mView?.showError(GlovoApplication.getAppContext().getString(R.string.error_communication))
            })
    }

}