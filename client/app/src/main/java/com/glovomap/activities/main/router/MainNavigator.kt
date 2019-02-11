package com.glovomap.main.activity.router

import android.support.v4.app.Fragment
import android.util.Log
import com.glovomap.R
import com.glovomap.activities.main.MainActivity
import com.glovomap.fragments.map.MapFragment
import com.glovomap.sia.rest.request.City

class MainNavigator(private val activity: MainActivity) {

    companion object {
        private val TAG = MainNavigator::class.java.simpleName
    }

    fun showMapView(city: City) {
        val fragment = MapFragment.getInstance(city)
        val tag = fragment::class.java.simpleName
        setFragment(fragment, tag)
    }

    private fun setFragment(fragment: Fragment, tag: String) {
        try {
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_content, fragment)
                .addToBackStack(tag)
                .commit()
            activity.supportFragmentManager.executePendingTransactions()
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    public fun clearBackStack() {
        val fm = activity.supportFragmentManager
        val count = fm.backStackEntryCount
        (0 until count).forEach { _ -> fm.popBackStack() }
    }
}