package com.glovomap.activities.main

import android.os.Bundle
import com.glovomap.R
import com.glovomap.activities.BaseActivity
import com.glovomap.activities.main.di.MainModule
import com.glovomap.di.components.DaggerMainComponent
import com.glovomap.di.components.MainComponent
import com.glovomap.sia.rest.request.City
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.activity_main), MainView {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    val component: MainComponent by lazy {
        DaggerMainComponent.builder()
            .parent(appComponent)
            .module(MainModule())
            .target(this)
            .build()
    }
    @Inject
    lateinit var presenter: MainPresenter

    override fun initComponents() {
        component.inject(this)
        presenter.bindView(this)

        var city = intent.extras.get("city") as City
        city.let { presenter.showMapView(city); }

    }

    override fun initData() {

    }

    override fun initFragments(savedInstanceState: Bundle?) {

    }

    override fun initListeners() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }
}