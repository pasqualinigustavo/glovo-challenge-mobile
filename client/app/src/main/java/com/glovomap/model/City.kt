package com.glovomap.sia.rest.request

import android.os.Parcel
import android.os.Parcelable


data class City(
    var working_area: List<String>,
    var code: String?,
    var name: String?,
    var country_code: String?,
    var language_code: String?,
    var busy: Boolean?,
    var time_zone: String?,
    var enabled: Boolean?,
    var currency: String?


) : Parcelable {

    constructor(source: Parcel) : this(
        source.createStringArrayList(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readValue(Boolean::class.java.classLoader) as Boolean?,
        source.readString(),
        source.readValue(Boolean::class.java.classLoader) as Boolean?,
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        dest.writeStringList(working_area)
        dest.writeString(code)
        dest.writeString(name)
        dest.writeString(country_code)
        dest.writeString(language_code)
        dest.writeValue(busy)
        dest.writeString(time_zone)
        dest.writeValue(enabled)
        dest.writeString(currency)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<City> = object : Parcelable.Creator<City> {
            override fun createFromParcel(source: Parcel): City = City(source)
            override fun newArray(size: Int): Array<City?> = arrayOfNulls(size)
        }
    }
}


