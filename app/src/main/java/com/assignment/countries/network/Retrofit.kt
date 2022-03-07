package com.assignment.countries.network

import com.assignment.countries.domain.*
import com.google.gson.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

internal class Retrofit {
    fun getApisClient(): GetApis {
        return createAwsRetrofit().create(GetApis::class.java)
    }

    @Throws(JsonParseException::class)
    private fun createAwsRetrofit(): Retrofit {
        val gson = GsonBuilder().apply {
            registerTypeAdapter(Currencies::class.java, CurrenciesAdapter())
            registerTypeAdapter(Name::class.java, NameAdapter())
            registerTypeAdapter(Languages::class.java, LanguagesAdapter())
            registerTypeAdapter(Translations::class.java, TransactionsAdapter())
            registerTypeAdapter(Demonyms::class.java, DemonymsAdapter())
            registerTypeAdapter(Gini::class.java, GiniAdapter())
        }.create()

        return Retrofit.Builder().apply {
            baseUrl("https://default.com")
            addConverterFactory(GsonConverterFactory.create(gson))
        }.build()
    }

    private inner class CurrenciesAdapter : JsonDeserializer<Any> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Any? {
            return json?.let {
                val currencies = Currencies()
                currencies.list = arrayListOf()

                (json as JsonObject).keySet().toList().forEach { key ->
                    val name = (json.get(key) as JsonObject).get("name")?.asString ?: "Na"
                    val symbol = (json.get(key) as JsonObject).get("symbol")?.asString ?: "Na"
                    currencies.list!!.add(Currency(key, name, symbol))
                }

                currencies
            }
        }
    }

    private inner class NameAdapter : JsonDeserializer<Any> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Any? {
            return json?.let {
                val common = json.asJsonObject.get("common")?.asString ?: "Na"
                val official = json.asJsonObject.get("official")?.asString ?: "Na"
                val nativeNameObject = json.asJsonObject.get("nativeName")?.asJsonObject

                val name = Name()
                name.common = common
                name.official = official
                name.nativeNames = arrayListOf()

                nativeNameObject?.let {
                    it.keySet().toList().forEach { key ->
                        val nativeNameOfficial = (it.get(key) as JsonObject).get("official")?.asString ?: "Na"
                        val nativeNameCommon = (it.get(key) as JsonObject).get("common")?.asString ?: "Na"
                        name.nativeNames!!.add(
                            NativeName(
                                key,
                                nativeNameCommon,
                                nativeNameOfficial
                            )
                        )
                    }
                }

                name
            }
        }
    }

    private inner class LanguagesAdapter : JsonDeserializer<Any> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Any? {
            return json?.let {
                val languages = Languages()
                languages.list = arrayListOf()

                (json as JsonObject).keySet().toList().forEach { key ->
                    val name = json.get(key)?.asString ?: "Na"
                    languages.list!!.add(Language(key, name))
                }

                languages
            }
        }
    }

    private inner class TransactionsAdapter : JsonDeserializer<Any> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Any? {
            return json?.let {
                val translations = Translations()
                translations.list = arrayListOf()

                (json as JsonObject).keySet().toList().forEach { key ->
                    val official = (json.get(key) as JsonObject).get("official")?.asString ?: "Na"
                    val common = (json.get(key) as JsonObject).get("common")?.asString ?: "Na"
                    translations.list!!.add(Translation(key, official, common))
                }

                translations
            }
        }
    }

    private inner class DemonymsAdapter : JsonDeserializer<Any> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Any? {
            return json?.let {
                val demonyms = Demonyms()
                demonyms.list = arrayListOf()

                (json as JsonObject).keySet().toList().forEach { key ->
                    val f = (json.get(key) as JsonObject).get("f")?.asString ?: "Na"
                    val m = (json.get(key) as JsonObject).get("m")?.asString ?: "Na"
                    demonyms.list!!.add(Demonym(key, f, m))
                }

                demonyms
            }
        }
    }

    private inner class GiniAdapter : JsonDeserializer<Any> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Any? {
            return json?.let {
                val year = (json as JsonObject).keySet().toList().firstOrNull()
                val value = json.get(year)?.asFloat ?: 0.0f
                Gini(year, value)
            }
        }
    }
}