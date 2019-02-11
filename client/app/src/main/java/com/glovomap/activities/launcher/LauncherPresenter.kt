package com.glovomap.activities.launcher

import android.content.Context
import android.location.LocationManager
import android.os.Build
import com.glovomap.activities.launcher.router.LauncherRouter
import com.glovomap.app.GlovoApplication
import com.glovomap.utils.SharedPreferencesUtils
import com.glovomap.utils.TSConstants

class LauncherPresenter(private val router: LauncherRouter) {

    var mView: LauncherView? = null

    fun bindView(view: LauncherView) {
        this.mView = view
    }

    fun unbindView() {
        this.mView = null
    }

    fun checkPermissionMap(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            !(!GlovoApplication.isAccessCoarseLocationPermissionGranted() || !GlovoApplication.isAccessFineLocationPermissionGranted())
        } else {
            true
        }
    }

    fun isProviderGPSEnabled(context: Context?): Boolean {
        val manager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun selectFlow(context: Context?) {
        if (!checkPermissionMap() || !isProviderGPSEnabled(context)) {
            val permissionMap = SharedPreferencesUtils.getInstance().hasTag(TSConstants.FIRST_TIME_PERMISSION_MAP)
            if (!permissionMap)
                router.showGPSEnableView()
            else router.showSelectCitiesView()
        } else router.showMainView()
    }
}