package com.glovomap.activities.selectLocation

import android.os.Bundle
import com.glovomap.R
import com.glovomap.activities.BaseActivity
import com.glovomap.activities.selectLocation.di.DaggerSelectLocationComponent
import com.glovomap.activities.selectLocation.di.SelectLocationComponent
import com.glovomap.activities.selectLocation.di.SelectLocationModule
import javax.inject.Inject

class SelectLocationActivity : BaseActivity(R.layout.activity_select_location), SelectLocationView {

    @Inject
    lateinit var presenter: SelectLocationPresenter

    val component: SelectLocationComponent by lazy {
        DaggerSelectLocationComponent.builder()
            .parent(appComponent)
            .module(SelectLocationModule())
            .target(this)
            .build()
    }

    override fun initListeners() {

    }

    override fun initFragments(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        presenter.openSelectCountryView()
    }

    override fun initComponents() {
        component.inject(this)
        presenter.bindView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

}
