package com.assignment.countries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CountryDetailScreen(countriesViewModel: CountriesViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(colorResource(id = R.color.colorPrimaryDark))
    ) {
        countriesViewModel.getSelectedCountry()?.let { country ->
            country.flag?.let { flag ->
                Column(modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp).fillMaxWidth()) {
                    Text(text = flag, fontSize = 40.sp)
                }
            }

            country.name?.common?.let { name ->
                Column(modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp).fillMaxWidth()) {
                    Text(text = stringResource(R.string.detailName), fontSize = 12.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                    Text(text = name, fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                }
            }

            country.capital?.firstOrNull()?.let { capital ->
                Column(modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 8.dp).fillMaxWidth()) {
                    Text(text = stringResource(R.string.detailCapital), fontSize = 12.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                    Text(text = capital, fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                }
            }

            country.region?.let { region ->
                Column(modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 8.dp).fillMaxWidth()) {
                    Text(text = stringResource(R.string.detailRegion), fontSize = 12.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                    Text(text = region, fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                }
            }

            country.population.let { population ->
                val test = appendMillions(population.toLong())
                Column(modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 8.dp).fillMaxWidth()) {
                    Text(text = stringResource(R.string.detailPopulation), fontSize = 12.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                    Text(text = test, fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                }
            }

            country.languages?.list?.let { languages ->
                Text(modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp), text = stringResource(R.string.detailLanguage), fontSize = 12.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                languages.forEach { language ->
                    language.name?.let { name ->
                        Text(modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp), text = name, fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                    }
                }
            }

            country.currencies?.list?.let { currencies ->
                Text(modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp), text = stringResource(R.string.detailCurrencies), fontSize = 12.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                currencies.forEach { currency ->
                    currency.name?.let { name ->
                        Row {
                            Text(modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp), text = name, fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                            currency.currency?.let { it ->
                                Text(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp), text = ", ".plus(it), fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                            }
                            currency.symbol?.let { symbol ->
                                Text(modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp), text = ", ".plus(symbol), fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            country.car?.let { car ->
                car.side?.let { side ->
                    Column(modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 8.dp).fillMaxWidth()) {
                        Text(text = stringResource(R.string.detailTraffic), fontSize = 12.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                        Text(text = side.replaceFirstChar { it.uppercase() }.plus(" ").plus(stringResource(R.string.detailHand)), fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                    }
                }

                car.signs?.let { signs ->
                    Text(modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp), text = stringResource(R.string.detailNumberPlateSign), fontSize = 12.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                    signs.forEach { sign ->
                        Text(modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp), text = sign, fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                    }
                }
            }

            country.timezones?.let { timezones ->
                Text(modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp), text = stringResource(R.string.detailTimezones), fontSize = 12.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)

                timezones.forEach { timezone ->
                    Text(modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp), text = timezone, fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

const val MILLION = 1000000L
const val BILLION = 1000000000L
const val TRILLION = 1000000000000L

fun appendMillions(x: Long): String {
    return when {
        x < MILLION -> x.toString()
        x < BILLION -> "${x.times(100).div(MILLION).times(0.01)} Million"
        x < TRILLION -> "${x.times(100).div(BILLION).times(0.01)} Billion"
        else -> "${x.times(100).div(TRILLION).times(0.01)} Trillion"
    }
}