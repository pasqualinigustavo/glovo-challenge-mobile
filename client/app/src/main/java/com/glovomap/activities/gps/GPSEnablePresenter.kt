package com.glovomap.activities.gps

import android.content.Context
import android.location.LocationManager
import android.os.Build
import com.glovomap.app.GlovoApplication
import com.glovomap.rest.ApiComm

class GPSEnablePresenter(val apiComm: ApiComm) {
    private var mView: GPSEnableView? = null

    fun bindView(view: GPSEnableView) {
        this.mView = view
    }

    fun onClickEnableGPS(context: Context) {
        if (isProviderGPSEnabled(context))
            mView?.verifyPermissionMap()
        else {
            mView?.verifyPermissionMap()
        }
    }

    fun checkPermissionMap(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            !(!GlovoApplication.isAccessCoarseLocationPermissionGranted() || !GlovoApplication.isAccessFineLocationPermissionGranted())
        } else {
            true
        }
    }

    fun isProviderGPSEnabled(context: Context): Boolean {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}