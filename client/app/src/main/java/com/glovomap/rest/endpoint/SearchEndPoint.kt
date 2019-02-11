package com.glovomap.sia.rest.endpoint

import com.glovomap.sia.rest.request.City
import com.glovomap.sia.rest.request.Country
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchEndPoint {
    @GET("/api/countries")
    fun getCountries(): Observable<List<Country>>

    @GET("/api/cities")
    fun getCities(): Observable<List<City>>

    @GET("/api/cities") //I don't understood why this endpoint returns N cities...
    fun getCity(@Query("city_code") cityId: String?): Observable<List<City>>
}