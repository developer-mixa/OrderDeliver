package com.example.orderdeliver.domain

import android.content.Context
import android.content.SharedPreferences
import com.example.orderdeliver.catch
import com.example.orderdeliver.data.repositories.DefaultAddressRepository
import com.example.orderdeliver.data.repositories.InvalidAddressException
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddressRepositoryTest() {
    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var preferences: SharedPreferences

    @RelaxedMockK
    lateinit var editor: SharedPreferences.Editor

    private lateinit var defaultAddressRepository : DefaultAddressRepository

    @Before
    fun setUp() {
        every { context.getSharedPreferences(any(), any()) } returns
                preferences
        every { preferences.edit() } returns editor

        defaultAddressRepository = DefaultAddressRepository(context)
    }

    @Test
    fun correctReturnCityTest() = runTest{
        every { defaultAddressRepository.getAddress() } returns "Сочи, улица Красная Горка, 18/2"

        defaultAddressRepository.setCity()
        val city = defaultAddressRepository.getCity().first()

        Assert.assertEquals("Сочи", city)
    }

    @Test
    fun throwInvalidAddressExceptionIfAddressIsNotValid() = runTest{
        every { defaultAddressRepository.getAddress() } returns " , - - "

        catch<InvalidAddressException> { defaultAddressRepository.setCity() }
    }

    @Test
    fun correctReturnNullCity() = runTest {
        every { defaultAddressRepository.getAddress() } returns "-, -, -"

        defaultAddressRepository.setCity()
        val city = defaultAddressRepository.getCity().first()

        Assert.assertEquals(DefaultAddressRepository.NOT_SELECTED_CITY, city)
    }

}