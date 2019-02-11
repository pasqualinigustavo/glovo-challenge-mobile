@file:JvmName("CitiesFragment")

package com.glovomap.activities.selectLocation.step2

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.glovomap.R
import com.glovomap.activities.selectLocation.step2.adapter.CityAdapter
import com.glovomap.activities.selectLocation.step2.di.CitiesModule
import com.glovomap.activities.selectLocation.step2.di.DaggerCitiesComponent
import com.glovomap.fragments.BaseFragment
import com.glovomap.sia.rest.request.City
import com.glovomap.sia.rest.request.Country
import com.glovomap.utils.AlertUtils
import kotlinx.android.synthetic.main.fragment_cities.*
import javax.inject.Inject

class CitiesFragment : BaseFragment(), CitiesView {

    lateinit var adapter: CityAdapter
    lateinit var country: Country

    companion object {
        val TAG = CitiesFragment::class.java.simpleName

        @JvmStatic
        fun getInstance(country: Country): CitiesFragment {
            val fragment = CitiesFragment()
            fragment.country = country;
            return fragment
        }

    }

    @Inject
    lateinit var presenter: CitiesPresenter

    override fun injectComponents() {
        DaggerCitiesComponent.builder()
            .selectLocationComponent(getSelectLocationComponent())
            .citiesModule(CitiesModule())
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_cities, container, false)
        initComponent(rootView, savedInstanceState)
        return rootView
    }


    override fun initData() {
        val lm = LinearLayoutManager(context)
        lm.orientation = LinearLayoutManager.VERTICAL
        fragment__recyclerview.layoutManager = lm

        initListeners()

        showProgressDialog()
        presenter.getCities(country)
    }

    override fun showCities(cities: List<City>) {
        hideProgressDialog()
        adapter = CityAdapter(cities)
        adapter.setOnItemClickListener(object : CityAdapter.OnItemClickListener {
            override fun onItemClickInfo(view: View, position: Int) {
                presenter.openMainView(adapter.getItem(position))
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