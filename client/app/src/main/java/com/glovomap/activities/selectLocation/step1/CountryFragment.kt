@file:JvmName("CountryFragment")

package com.glovomap.activities.selectLocation.step1

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.glovomap.R
import com.glovomap.activities.selectLocation.step1.adapter.CountryAdapter
import com.glovomap.activities.selectLocation.step1.di.CountryModule
import com.glovomap.activities.selectLocation.step1.di.DaggerCountryComponent
import com.glovomap.fragments.BaseFragment
import com.glovomap.sia.rest.request.Country
import com.glovomap.utils.AlertUtils
import kotlinx.android.synthetic.main.fragment_cities.*
import javax.inject.Inject

class CountryFragment : BaseFragment(), CountryView {

    lateinit var adapter: CountryAdapter

    companion object {
        val TAG = CountryFragment::class.java.simpleName

        @JvmStatic
        fun getInstance() = CountryFragment()
    }

    @Inject
    lateinit var presenter: CountryPresenter

    override fun injectComponents() {
        DaggerCountryComponent.builder()
            .selectLocationComponent(getSelectLocationComponent())
            .countryModule(CountryModule())
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_countries, container, false)
        initComponent(rootView, savedInstanceState)
        return rootView
    }


    override fun initData() {
        val lm = LinearLayoutManager(context)
        lm.orientation = LinearLayoutManager.VERTICAL
        fragment__recyclerview.layoutManager = lm

        initListeners()

        showProgressDialog()
        presenter.getCountries()
    }

    override fun showCountries(countries: List<Country>) {
        hideProgressDialog()
        adapter = CountryAdapter(countries)
        adapter.setOnItemClickListener(object : CountryAdapter.OnItemClickListener {
            override fun onItemClickInfo(view: View, position: Int) {
                presenter.openSelectCityView(adapter.getItem(position))
            }
        })

        fragment__recyclerview.setAdapter(adapter)
    }

    override fun initListeners() {

    }

    override fun initComponent(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "initComponents")
        presenter.bindView(this)
    }

    override fun showError(message: String) {
        hideProgressDialog()
        AlertUtils.showMessage(context!!, message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbindView()
    }
}