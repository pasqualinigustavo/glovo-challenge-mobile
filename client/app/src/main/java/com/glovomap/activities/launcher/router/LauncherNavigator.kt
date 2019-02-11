package com.glovomap.main.activity.router

import android.content.Intent
import com.glovomap.activities.gps.GPSEnableActivity
import com.glovomap.activities.launcher.LauncherActivity
import com.glovomap.activities.main.MainActivity

class LauncherNavigator(private val activity: LauncherActivity) {

    companion object {
        private val TAG = LauncherNavigator::class.java.simpleName
    }

    fun showMainView() {
        val i = Intent(activity, MainActivity::class.java)
        activity.startActivity(i)
        activity.finish()
    }

    fun showSelectCitiesView() {
        val i = Intent(activity, MainActivity::class.java)
        activity.startActivity(i)
        activity.finish()
    }

    fun showGPSEnableView() {
        val i = Intent(activity, GPSEnableActivity::class.java)
        activity.startActivity(i)
        activity.finish()
    }

}