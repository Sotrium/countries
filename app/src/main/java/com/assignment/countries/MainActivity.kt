package com.assignment.countries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.assignment.countries.domain.Countries
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val weatherViewModel = ViewModelProvider(this)[CountriesViewModel::class.java]
        weatherViewModel.loadCountries()

        setContent {
            Scaffold {
                Navigation(weatherViewModel)
            }
        }
    }

    @Composable
    fun CountryListScreen(navController: NavController, countriesLiveData: CountriesViewModel) {
        val countriesState by countriesLiveData.countries.collectAsState(initial = emptyList())

        if (countriesState.isEmpty()) {
            LoadingComponent()
        } else {
            CountriesComponent(navController, countriesLiveData, countriesState)
        }
    }

    @Composable
    fun CountriesComponent(navController: NavController, countriesLiveData: CountriesViewModel, countries: List<Countries>) {
        val textState = remember { mutableStateOf(TextFieldValue("")) }

        Column {
            SearchView(textState)
            CountryList(navController, textState, countries, countriesLiveData)
        }
    }

    @Composable
    fun CountryList(navController: NavController, state: MutableState<TextFieldValue>, countries: List<Countries>, countriesLiveData: CountriesViewModel) {
        var filteredCountries: MutableList<Countries>

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            val searchedText = state.value.text
            filteredCountries = if (searchedText.isEmpty()) {
                countries.toMutableList()
            } else {
                val resultList = mutableListOf<Countries>()
                for (country in countries) {
                    if (country.name!!.common!!.lowercase(Locale.getDefault()).contains(searchedText.lowercase(Locale.getDefault()))) {
                        resultList.add(country)
                    }
                }
                resultList
            }
            items(filteredCountries.size) { index ->
                CountryListItem(
                    countryText = filteredCountries[index].name?.common ?: "Na",
                    countryFlag = filteredCountries[index].flag ?: "",
                    onItemClick = { selectedCountry ->

                        countriesLiveData.saveSelectedCountry(filteredCountries[index])
                        navController.navigate("details/$selectedCountry") {
                            popUpTo("main") {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun CountryListItem(countryText: String, countryFlag: String, onItemClick: (String) -> Unit) {
        Row(
            modifier = Modifier
                .clickable(onClick = { onItemClick(countryText) })
                .background(colorResource(id = R.color.colorPrimaryDark))
                .padding(8.dp, 16.dp, 8.dp, 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = countryFlag, fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterVertically))
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = countryText, fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis)
        }
    }

    @Composable
    fun Navigation(countriesLiveData: CountriesViewModel) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                CountryListScreen(navController, countriesLiveData)
            }
            composable("details/{countryName}") {
                CountryDetailScreen(countriesLiveData)
            }
        }
    }

    @Composable
    fun SearchView(state: MutableState<TextFieldValue>) {
        TextField(
            value = state.value,
            onValueChange = { value ->
                state.value = value
            },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            state.value =
                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                cursorColor = Color.White,
                leadingIconColor = Color.White,
                trailingIconColor = Color.White,
                backgroundColor = colorResource(id = R.color.colorPrimary),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }


    @Composable
    fun LoadingComponent() {
        Column(
            modifier = Modifier.fillMaxSize().background(colorResource(id = R.color.colorPrimaryDark)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            CircularProgressIndicator(modifier = Modifier.wrapContentWidth(CenterHorizontally), color = Color.White)
            Text(
                text = stringResource(R.string.loading_message),
                modifier = Modifier.padding(16.dp),
                style = TextStyle(fontSize = 16.sp),
                color = Color.White,
            )
        }
    }
}