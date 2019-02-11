package com.glovomap.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import com.glovomap.R
import com.glovomap.activities.main.MainActivity
import com.glovomap.activities.selectLocation.SelectLocationActivity
import com.glovomap.activities.selectLocation.di.SelectLocationComponent
import com.glovomap.di.components.MainComponent

abstract class BaseFragment() : Fragment() {

    private var progressDialog: MaterialDialog? = null
    private var hasContext = false

    fun getMainComponent(): MainComponent? {
        val activity = activity
        return if (activity is MainActivity) {
            activity.component
        } else null
    }

    fun getSelectLocationComponent(): SelectLocationComponent? {
        val activity = activity
        return if (activity is SelectLocationActivity) {
            activity.component
        } else null
    }

    override fun onDetach() {
        super.onDetach()
        hasContext = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        tryInjection(activity)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init(savedInstanceState)
    }

    protected fun init(savedInstanceState: Bundle?) {
        this.initComponent(this.view, savedInstanceState)
        this.initData()
        this.initListeners()
    }

    protected abstract fun initComponent(view: View?, savedInstanceState: Bundle?)

    protected abstract fun initListeners()

    protected abstract fun initData()

    abstract fun injectComponents()

    private fun tryInjection(context: Context?) {
        if (!hasContext && context != null) {
            hasContext = true
            injectComponents()
        }
    }

    override fun onStop() {
        super.onStop()
        System.gc()
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            if (progressDialog != null) {
                progressDialog!!.dismiss()
            }
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, e.message, e)
        }

    }

    fun showProgressDialog() {
        if (context != null) {
            val b = MaterialDialog.Builder(context!!)
            b.title(R.string.label_please_wait)
            b.cancelable(false)
            b.theme(Theme.LIGHT)
            b.content(R.string.label_loading)
            b.progress(true, 0)
            progressDialog = b.show()
        }
    }

    fun hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog!!.hide()
        }
    }
}
