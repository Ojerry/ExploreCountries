package com.ojinc.explorecountries.data

import com.google.gson.annotations.SerializedName

data class CountryDataItem(

    @SerializedName("name")
    val name: Name,
    val altSpellings: List<String>,
    @SerializedName("area")
    val area: Double,
////    val borders: List<String>,
    @SerializedName("capital")
    val capital: List<String>,
//    val capitalInfo: CapitalInfo,
////    val car: Car,
////    val cca2: String,
////    val cca3: String,
////    val ccn3: String,
////    val cioc: String,
    @SerializedName("coatOfArms")
    val coatOfArms: CoatOfArms,
    @SerializedName("continents")
    val continents: List<String>,
    @SerializedName("currencies")
    val currencies: Currencies,
////    val demonyms: Demonyms,
////    val fifa: String,
//    val flag: String,
    @SerializedName("flags")
    val flags: Flags,
////    val gini: Gini,
////    val idd: Idd,
    @SerializedName("independent")
    val independent: Boolean,
    @SerializedName("landlocked")
    val landlocked: Boolean,
//    val languages: Languages,
//    val latlng: List<Any>,
//    val maps: Maps,
    @SerializedName("population")
    val population: Int,
//    val postalCode: PostalCode,
    @SerializedName("region")
    val region: String,
//    val startOfWeek: String,
//    val status: String,
    @SerializedName("subregion")
    val subregion: String,
    @SerializedName("timezones")
    val timezones: List<String>,
//    val tld: List<String>,
//    val translations: Translations,
    @SerializedName("unMember")
    val unMember: Boolean
)