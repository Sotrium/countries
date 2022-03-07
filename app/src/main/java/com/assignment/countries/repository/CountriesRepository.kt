package com.assignment.countries.repository

import com.assignment.countries.domain.Countries
import com.assignment.countries.network.Retrofit
import retrofit2.Call

internal class CountriesRepository {
    fun fetchCountries(): Call<List<Countries>> {
        return Retrofit().getApisClient().fetchCountries("https://restcountries.com/v3.1/all")
    }
}