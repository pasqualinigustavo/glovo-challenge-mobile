package com.glovomap.activities.gps

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.widget.Button
import android.widget.TextView
import com.glovomap.R
import com.glovomap.activities.BaseActivity
import com.glovomap.activities.gps.di.DaggerGpsEnableComponent
import com.glovomap.activities.gps.di.GPSEnableModule
import com.glovomap.activities.gps.di.GpsEnableComponent
import com.glovomap.app.GlovoApplication
import com.glovomap.utils.SharedPreferencesUtils
import com.glovomap.utils.TSConstants
import javax.inject.Inject

class GPSEnableActivity : BaseActivity(R.layout.activity_enable_location), GPSEnableView {

    @Inject
    lateinit var presenter: GPSEnablePresenter

    var buttonEnableLocation: Button? = null
    var fragment_gsp_disabled_textview_message: TextView? = null

    val component: GpsEnableComponent by lazy {
        DaggerGpsEnableComponent.builder()
            .parent(appComponent)
            .module(GPSEnableModule())
            .target(this)
            .build()
    }

    override fun initComponents() {
        component.inject(this)
        presenter.bindView(this)

        buttonEnableLocation = findViewById(R.id.buttonEnableLocation)
        fragment_gsp_disabled_textview_message = findViewById(R.id.fragment_gsp_disabled_textview_message)
    }

    override fun initListeners() {
        buttonEnableLocation!!.setOnClickListener { presenter.onClickEnableGPS(this) }
        setTexts()
    }

    override fun initFragments(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }

    override fun verifyPermissionMap() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!GlovoApplication.isAccessCoarseLocationPermissionGranted() || !GlovoApplication.isAccessFineLocationPermissionGranted()
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    TSConstants.GPS
                )
            } else run { setFinishResult(Activity.RESULT_OK) }
        } else run { setFinishResult(Activity.RESULT_OK) }
    }

    override fun openSettings() {
        try {
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(callGPSSettingIntent, 1)
        } catch (e: Exception) {

        }
    }

    fun setTexts() {
        val permissionMap = SharedPreferencesUtils.getInstance().hasTag(TSConstants.FIRST_TIME_PERMISSION_MAP)
        if (!presenter.checkPermissionMap() && permissionMap) {
            fragment_gsp_disabled_textview_message!!.setText(getString(R.string.fragment_gps_disable_text_authorize_location))
        } else {
            fragment_gsp_disabled_textview_message!!.setText(getString(R.string.fragment_gps_disable_text_need_location))
        }
        if (presenter.isProviderGPSEnabled(this))
            buttonEnableLocation!!.setText(getString(R.string.fragment_gps_disable_button_ok_lbl))
        else {
            buttonEnableLocation!!.setText(getString(R.string.fragment_gps_disable_button_ok_lbl_gps_disable))
        }

    }

    private fun setFinishResult(result: Int) {
        setResult(result)
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            TSConstants.GPS -> {
                SharedPreferencesUtils.getInstance().save(TSConstants.FIRST_TIME_PERMISSION_MAP, false)
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setFinishResult(Activity.RESULT_OK)
                } else setFinishResult(Activity.RESULT_CANCELED)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            setTexts()
            verifyPermissionMap()
        }
    }
}