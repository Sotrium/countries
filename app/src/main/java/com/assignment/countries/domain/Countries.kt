package com.assignment.countries.domain

data class NativeName(
    var name: String? = null,
    var official: String? = null,
    var common: String? = null
)

data class Name(
    var common: String? = null,
    var official: String? = null,
    var nativeNames: ArrayList<NativeName>? = null
)

data class Currency(
    val currency: String? = null,
    var name: String? = null,
    var symbol: String? = null
)

data class Currencies(var list: ArrayList<Currency>? = null)

data class Idd(
    var root: String? = null,
    var suffixes: ArrayList<String>? = null
)

data class Language(var abr: String? = null, var name: String? = null)

data class Languages(var list: ArrayList<Language>? = null)

data class Translation(
    val name: String? = null,
    var official: String? = null,
    var common: String? = null
)

data class Translations(var list: ArrayList<Translation>? = null)

data class Demonym(
    val name: String? = null,
    var f: String? = null,
    var m: String? = null
)

data class Demonyms(var list: ArrayList<Demonym>? = null)

data class Maps(
    var googleMaps: String? = null,
    var openStreetMaps: String? = null
)

data class Gini(
    var year: String? = null,
    var value: Float? = null
)

data class Car(
    var signs: ArrayList<String>? = null,
    var side: String? = null
)

data class Flags(
    var png: String? = null,
    var svg: String? = null
)

data class CoatOfArms(
    var png: String? = null,
    var svg: String? = null
)

data class CapitalInfo(var latlng: ArrayList<Double>? = null)

data class PostalCode(
    var format: String? = null,
    var regex: String? = null
)

data class Countries(
    var name: Name? = null,
    var tld: ArrayList<String>? = null,
    var cca2: String? = null,
    var ccn3: String? = null,
    var cca3: String? = null,
    var cioc: String? = null,
    var independent: Boolean = false,
    var status: String? = null,
    var unMember: Boolean = false,
    var currencies: Currencies? = null,
    var idd: Idd? = null,
    var capital: ArrayList<String>? = null,
    var altSpellings: ArrayList<String>? = null,
    var region: String? = null,
    var subregion: String? = null,
    var languages: Languages? = null,
    var translations: Translations? = null,
    var latlng: ArrayList<Double>? = null,
    var landlocked:Boolean = false,
    var borders: ArrayList<String>? = null,
    var area: Float = 0.0f,
    var demonyms: Demonyms? = null,
    var flag: String? = null,
    var maps: Maps? = null,
    var population: Int = 0,
    var gini: Gini? = null,
    var fifa: String? = null,
    var car: Car? = null,
    var timezones: ArrayList<String>? = null,
    var continents: ArrayList<String>? = null,
    var flags: Flags? = null,
    var coatOfArms: CoatOfArms? = null,
    var startOfWeek: String? = null,
    var capitalInfo: CapitalInfo? = null,
    var postalCode: PostalCode? = null
)

