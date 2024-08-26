package com.example.orderdeliver.data.repositories

import android.content.Context
import android.content.SharedPreferences
import com.example.orderdeliver.R
import com.example.orderdeliver.domain.repositories.AddressRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAddressRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : AddressRepository {

    private val preferences: SharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
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
        return if (street == "-") context.getString(R.string.undefined_city)
        else street
    }

    override fun getAddress(): String {
        return preferences.getString(PREF_ADDRESS_VALUE, "-, -, -. -") ?: ""
    }

    override fun getCountry(): String {
        return "Россия"
    }

    override fun getCity(): Flow<String> = cityFlow

    private companion object{
        const val APP_PREFERENCES = "app_preferences"
        const val PREF_ADDRESS_VALUE = "pref_address_value"
    }



}

class InvalidAddressException : Exception("Invalid address exception")