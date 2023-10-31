package com.example.orderdeliver.data

import android.content.Context
import android.content.SharedPreferences
import com.example.orderdeliver.domain.AddressRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAddressRepository @Inject constructor(
    @ApplicationContext appContext: Context
) : AddressRepository {

    private val preferences: SharedPreferences = appContext.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    private val cityFlow = MutableStateFlow(getCurrentCity())

    override suspend fun writeAddress(address: String) {
        preferences.edit().putString(PREF_ADDRESS_VALUE, address).apply()
        setCity()
    }

    override suspend fun setCity() {
        cityFlow.emit(getCurrentCity())
    }

    private fun getCurrentCity(): String{
        val address = getAddress()
        val street = address.split(",")[0]
        if (street.isBlank()) throw InvalidAddressException()
        return if (street == "-") NOT_SELECTED_CITY
        else street
    }

    override fun getAddress(): String {
        return preferences.getString(PREF_ADDRESS_VALUE, "-, -, -. -") ?: ""
    }

    override fun getCountry(): String {
        return "Россия"
    }

    override fun getCity(): Flow<String> = cityFlow

    companion object{
        private const val APP_PREFERENCES = "app_preferences"
        private const val PREF_ADDRESS_VALUE = "pref_address_value"

        const val NOT_SELECTED_CITY = "Не выбран город"
    }



}

class InvalidAddressException : Exception("Invalid address exception")