package com.glovomap.activities.selectLocation.router

import android.content.Intent
import com.glovomap.R
import com.glovomap.activities.main.MainActivity
import com.glovomap.activities.selectLocation.SelectLocationActivity
import com.glovomap.fragments.BaseFragment
import com.glovomap.sia.rest.request.City

class SelectLocationNavigator(private val activity: SelectLocationActivity) {

    companion object {
        private val TAG = SelectLocationNavigator::class.java.simpleName
    }

    fun switchContent(fragment: BaseFragment, addToBackStack: Boolean) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        val tag = fragment.javaClass.canonicalName
        transaction.replace(R.id.activity_content, fragment, tag)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }

    fun openMainView(city: City) {
        val intent = Intent(activity, MainActivity::class.java)
        intent.putExtra("city",city)
        intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME)
        activity.startActivity(intent)
        activity.finish()
    }
}