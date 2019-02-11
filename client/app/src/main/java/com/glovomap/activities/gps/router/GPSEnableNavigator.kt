package com.glovomap.activities.gps.router

import android.content.Intent
import com.glovomap.activities.gps.GPSEnableActivity
import com.glovomap.activities.main.MainActivity
import com.glovomap.activities.selectLocation.SelectLocationActivity

class GPSEnableNavigator(private val activity: GPSEnableActivity) {
    companion object {
        private val TAG = GPSEnableNavigator::class.java.simpleName
    }

    fun showMainView() {
        val i = Intent(activity, MainActivity::class.java)
        activity.startActivity(i)
        activity.finish()
    }

    fun showSelectCitiesView() {
        val i = Intent(activity, SelectLocationActivity::class.java)
        activity.startActivity(i)
        activity.finish()
    }
}