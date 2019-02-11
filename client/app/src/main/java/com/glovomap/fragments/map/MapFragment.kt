@file:JvmName("MapFragment")

package com.glovomap.fragments.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import com.glovomap.R
import com.glovomap.fragments.BaseFragment
import com.glovomap.fragments.map.di.DaggerMapFragmentComponent
import com.glovomap.fragments.map.di.MapFragmentModule
import com.glovomap.sia.rest.request.City
import com.glovomap.utils.AlertUtils
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.*
import javax.inject.Inject

class MapFragment : BaseFragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener,
    MapFragmentView, GoogleMap.OnPolygonClickListener {

    var city: City? = null

    companion object {
        val TAG = MapFragment::class.java.simpleName

        @JvmStatic
        fun getInstance(city: City?): MapFragment {
            val fragment = MapFragment()
            fragment.city = city;
            return fragment
        }

        private val COLOR_GREEN_ARGB = -0xc771c4
        private val POLYGON_STROKE_WIDTH_PX = 8
        private val PATTERN_DASH_LENGTH_PX = 20
        private val PATTERN_GAP_LENGTH_PX = 20

        private val DASH = Dash(PATTERN_DASH_LENGTH_PX.toFloat())
        private val GAP = Gap(PATTERN_GAP_LENGTH_PX.toFloat())

        // Create a stroke pattern of a gap followed by a dash.
        private val PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH)

        private val UPDATE_INTERVAL = (30 * 1000).toLong()  /* 10 secs */
        private val FASTEST_INTERVAL: Long = 5000 /* 2 sec */
    }

    var mapFragment: SupportMapFragment? = null
    var mGoogleApiClient: GoogleApiClient? = null
    var mMap: GoogleMap? = null
    var mapLoaded: Boolean = false
    var onBoardingFinished: Boolean = false
    var mLocationRequest: LocationRequest? = null
    var mapReady: Boolean = false

    @Inject
    lateinit var presenter: MapPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        super.onCreateView(inflater, container, savedInstanceState)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mGoogleApiClient = GoogleApiClient.Builder(context!!)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .build()
        mGoogleApiClient!!.connect()
    }

    override fun injectComponents() {
        DaggerMapFragmentComponent.builder()
            .mainComponent(getMainComponent())
            .mapFragmentModule(MapFragmentModule())
            .build()
            .inject(this)
    }

    override fun initComponent(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "initComponent")
        presenter.attachView(this)
    }

    override fun initListeners() {

    }

    override fun initData() {
        initMapFragment()
        mapFragment?.getMapAsync(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    private fun initMapFragment() {
        val fm = childFragmentManager
        mapFragment = childFragmentManager.findFragmentByTag("mapFragment") as? SupportMapFragment
        if (mapFragment == null) {
            mapFragment = SupportMapFragment()
            val ft = fm.beginTransaction()
            ft.add(R.id.mapContainer, mapFragment, "mapFragment")
            ft.commit()
            fm.executePendingTransactions()
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        try {
            if (googleMap != null) {
                mMap = googleMap
                mMap!!.setOnMapLoadedCallback {
                    mapLoaded = true
                    if (city != null) {
                        showProgressDialog()
                        presenter.downloadCityInfo(city!!)
                    }
                }
                mMap!!.uiSettings.isMapToolbarEnabled = false
                mMap!!.uiSettings.isZoomControlsEnabled = true
                mapReady = true
                if (mapLoaded) {
                    onBoardingFinished = true
                }
            }
        } catch (e: Exception) {
            Log.e(MapFragment::class.java.simpleName, "Error map creation", e)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume...")
        if (onBoardingFinished)
            setUpMapIfNeeded()
    }

    private fun setUpMapIfNeeded() {
        if (mMap == null && mapFragment != null) {
            mapFragment?.getMapAsync(this)
        }
    }

    override fun onConnected(bundle: Bundle?) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }
        startLocationUpdates()
    }

    override fun onConnectionSuspended(i: Int) {

    }

    protected fun startLocationUpdates() {

        if (mLocationRequest == null) {
            // Create the location request
            mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)

            // Request location updates
            if (Build.VERSION.SDK_INT >= 23) {
                if (ActivityCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest, this
            )
        }
    }

    override fun showError(message: String) {
        hideProgressDialog()
        AlertUtils.showMessage(context!!, message)
    }

    override fun readCity(city: City) {
        this.city = city
        showProgressDialog()
        presenter.downloadCityInfo(city)
    }

    override fun showErrorLocation(message: String) {
        hideProgressDialog()
        MaterialDialog.Builder(context!!)
            .title(R.string.app_name)
            .theme(Theme.LIGHT)
            .content(message)
            .positiveText(R.string.label_ok)
            .onPositive({ dialog, which -> presenter.openSelectLocationView() })
            .show()
    }

    override fun showCity(city: City) {
        hideProgressDialog()
        fragment_map__cityname.text = getString(R.string.cityname, city.name, city.country_code)
        fragment_map__language_code.text = getString(R.string.language_code, city.language_code)
        fragment_map__currency.text = getString(R.string.currency_code, city.currency)
        var builder: LatLngBounds.Builder = LatLngBounds.Builder()

        for (item in city.working_area) {
            if (item != null) {
                var pointList: MutableList<LatLng>? = null
                pointList = presenter.decodePoly(item)
                pointList.let {
                    if (!pointList.isEmpty()) {
                        var polygonOpt = PolygonOptions()
                        for (point in pointList!!) {
                            polygonOpt.points.add(point)
                            builder.include(point)
                        }
                        polygonOpt.clickable(true)
                        var polygon = mMap!!.addPolygon(polygonOpt)
                        mMap!!.setOnPolygonClickListener(this)
                        // Style the polygon.
                        stylePolygon(polygon)
                    }
                }
            }
        }
        var bounds: LatLngBounds = builder.build()
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15))
    }

    private fun stylePolygon(polygon: Polygon) {
        var pattern: List<PatternItem>? = null
        var strokeColor = COLOR_GREEN_ARGB
        pattern = PATTERN_POLYGON_ALPHA

        polygon.strokePattern = pattern
        polygon.strokeWidth = POLYGON_STROKE_WIDTH_PX.toFloat()
        polygon.strokeColor = strokeColor
        polygon.fillColor = Color.argb(127, 0, 255, 0)
    }

    override fun onLocationChanged(location: Location) {
        location.let {
            updateSelfLocation(LatLng(location.latitude, location.longitude))
        }
    }

    private fun updateSelfLocation(currentLocation: LatLng) {
        mGoogleApiClient?.disconnect()
        if (city == null) {
            showProgressDialog()
            presenter.searchCurrentCity(context, currentLocation)
        }
    }

    override fun onPolygonClick(polygon: Polygon) {
        // Flip the values of the red, green, and blue components of the polygon's color.
        var color = polygon.strokeColor xor 0x00ffffff
        polygon.strokeColor = color
        color = polygon.fillColor xor 0x00ffffff
        polygon.fillColor = color
    }
}