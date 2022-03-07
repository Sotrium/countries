package com.assignment.countries.network

import com.assignment.countries.domain.Countries
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface GetApis {
    @GET()
    fun fetchCountries(@Url url: String): Call<List<Countries>>
}