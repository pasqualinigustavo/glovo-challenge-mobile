package com.glovomap.fragments.map

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.glovomap.R
import com.glovomap.app.GlovoApplication
import com.glovomap.fragments.map.router.MapRouter
import com.glovomap.sia.rest.request.City
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.text.Normalizer
import java.util.*

class MapPresenter(
    private val interactor: MapInteractor,
    private val router: MapRouter
) {

    companion object {
        val TAG = MapPresenter::class.java.simpleName
    }

    private var mView: MapFragmentView? = null

    fun attachView(view: MapFragmentView) {
        this.mView = view
        Log.d(TAG, "attachView")
    }

    fun detachView() {
        this.mView = null
    }

    fun downloadCityInfo(city: City) {
        Log.d(TAG, "downloadCityInfo")
        interactor.cityInfo(city.code)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                if (response != null) {
                    filterCities(response, city)
                } else {
                    mView?.showError(GlovoApplication.getAppContext().getString(R.string.error_communication))
                }
            }, { error ->
                mView?.showError(GlovoApplication.getAppContext().getString(R.string.error_communication))
            })
    }

    private fun filterCities(citiesOrg: List<City>, city: City) {
        for (item in citiesOrg) {
            if (item.code == city.code)
                mView?.showCity(item)
        }
    }

    fun searchCurrentCity(context: Context?, currentLocation: LatLng) {
        Log.d(TAG, "searchCurrentCity")
        interactor.cityList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                if (response != null) {
                    getCityName(context, currentLocation, response)
                } else if (response == null) {
                    mView?.showErrorLocation(context!!.getString(R.string.city_not_found))
                }
            }, { error ->
                mView?.showErrorLocation(context!!.getString(R.string.city_not_found))
            })
    }

    fun getCityName(context: Context?, currentLocation: LatLng, cities: List<City>) {
        Log.d(TAG, "getCityName")
        val gcd = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>?
        try {
            addresses = gcd.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1)
            if (addresses != null && addresses.size > 0 && addresses[0].locality != null) {
                var cityName = addresses[0].locality.toString()
                if (cityName != null && !cities.isEmpty()) {
                    cityName = Normalizer.normalize(cityName, Normalizer.Form.NFD)
                    cityName = cityName.replace("[^\\p{ASCII}]".toRegex(), "")
                    for (item in cities) {
                        if (item.name == cityName)
                            mView?.readCity(item)
                    }
                } else mView?.showErrorLocation(context!!.getString(R.string.city_not_found))

            }
        } catch (e: IOException) {
            e.printStackTrace()
            mView?.showErrorLocation(context!!.getString(R.string.city_not_found))
        }

    }

    fun openSelectLocationView() {
        router.showSelectLocationView()
    }

    fun decodePoly(encoded: String): MutableList<LatLng> {
        return PolyUtil.decode(encoded)
    }
}