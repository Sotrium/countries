package com.assignment.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.countries.domain.Countries
import com.assignment.countries.repository.CountriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesViewModel : ViewModel() {
    private val countriesRepository = CountriesRepository()
    private var selectedCountry: Countries? = null
    val countries: MutableStateFlow<List<Countries>> by lazy { MutableStateFlow(listOf()) }

    fun loadCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            val call = countriesRepository. fetchCountries()
            call.enqueue(object : Callback<List<Countries>> {
                override fun onResponse(call: Call<List<Countries>>, response: Response<List<Countries>>) {
                    viewModelScope.launch(Dispatchers.IO) {
                        if (response.isSuccessful && response.body() != null) {
                            countries.emit(response.body()!!.sortedBy { it.name?.common })
                        } else {
                            countries.emit(emptyList())
                        }
                    }
                }

                override fun onFailure(call: Call<List<Countries>>, t: Throwable) {
                    viewModelScope.launch(Dispatchers.IO) {
                        countries.emit(emptyList())
                    }
                }
            })
        }
    }

    fun saveSelectedCountry(selectedCountry: Countries) {
        this.selectedCountry = selectedCountry
    }

    fun getSelectedCountry() = selectedCountry
}