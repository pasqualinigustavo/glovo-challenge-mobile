package com.glovomap.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.glovomap.R
import com.glovomap.app.GlovoApplication
import com.glovomap.di.components.ApplicationComponent
import com.glovomap.di.modules.ActivityModule

abstract class BaseActivity(var layoutId: Int) : AppCompatActivity() {

    protected var progressDialog: ProgressDialog? = null

    protected val appComponent: ApplicationComponent
        get() = (application as GlovoApplication).applicationComponent

    protected val activityModule: ActivityModule
        get() = ActivityModule(this)

    /**
     * Method responsible to start the activity and define the behavior of the activity
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this.layoutId)

        // The Theme's windowBackground is masked by the opaque background of the activity, and the windowBackground causes an
        // uncecessarry overdraw. Nulllfying the windowBackground removes that background
        window.setBackgroundDrawable(null)

        init(savedInstanceState)
    }

    protected fun init(savedInstanceState: Bundle?) {
        initComponents()
        initFragments(savedInstanceState)
        initListeners()
        initData()
    }

    protected abstract fun initComponents()

    protected abstract fun initFragments(savedInstanceState: Bundle?)

    protected abstract fun initData()

    protected abstract fun initListeners()

    fun showProgressBar() {
        if (progressDialog == null || !progressDialog!!.isShowing) {
            val dialog = ProgressDialog(this)

            dialog.setMessage(getString(R.string.label_please_wait))
            dialog.isIndeterminate = true
            dialog.setCancelable(false)
            dialog.show()

            progressDialog = dialog
        }
    }

    fun hideProgressBar() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
    }

}
