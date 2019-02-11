package com.glovomap.activities.launcher

import android.os.Bundle
import com.glovomap.R
import com.glovomap.activities.BaseActivity
import com.glovomap.activities.launcher.di.DaggerLauncherComponent
import com.glovomap.activities.launcher.di.LauncherComponent
import com.glovomap.activities.launcher.di.LauncherModule
import javax.inject.Inject

class LauncherActivity : BaseActivity(R.layout.activity_launcher), LauncherView {

    companion object {
        val TAG: String = LauncherActivity::class.java.simpleName
    }

    val component: LauncherComponent by lazy {
        DaggerLauncherComponent.builder()
            .parent(appComponent)
            .module(LauncherModule())
            .target(this)
            .build()
    }

    @Inject
    lateinit var presenter: LauncherPresenter

    override fun initComponents() {
        component.inject(this)
        presenter.bindView(this)
        presenter.selectFlow(this)
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