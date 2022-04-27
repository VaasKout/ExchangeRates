package com.example.exchangerate.data.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.exchangerate.data.DataState
import com.example.exchangerate.data.cache.ExchangeDao
import com.example.exchangerate.data.cache.SortedState
import com.example.exchangerate.data.network.ExchangeRatesApi
import com.example.exchangerate.mappers.toExchangeEntity
import com.example.exchangerate.mappers.toExchangeRate
import com.example.exchangerate.mappers.toRateEntity
import com.example.exchangerate.model.ExchangeRate
import com.example.exchangerate.utils.CURRENCY
import com.example.exchangerate.utils.DEFAULT_PREFS
import com.example.exchangerate.utils.FLAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ExchangeRepository(
    private val exchangeDao: ExchangeDao,
    private val exchangeRatesApi: ExchangeRatesApi,
    appContext: Context
) {

    private val sharedPreferences: SharedPreferences =
        appContext.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)

    fun loadRemoteLatestItems() = flow {
        emit(DataState.Loading)
        try {
            val currency = sharedPreferences.getString(CURRENCY, "") ?: ""
            val dtoData = exchangeRatesApi.getLatest(currency)
            if (!dtoData.success) {
                throw Exception("Api key's expired, try to do another request later")
            }

            try {
                val dbData = exchangeDao.getAllRates()
                if (dbData.size < dtoData.toExchangeEntity().size) {
                    exchangeDao.insertRateList(dtoData.toExchangeEntity())
                } else {
                    dtoData.toExchangeEntity().forEach {
                        exchangeDao.updateRateEntity(it.currency, it.rate)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            emit(DataState.Success(dtoData))
        } catch (e: Exception) {
            e.printStackTrace()
            when {
                e.message.toString().contains("400") -> {
                    emit(DataState.Error("Invalid Request"))
                }
                e.message.toString().contains("429") -> {
                    emit(DataState.Error("Api key's expired, try to do another request later"))
                }
                else -> {
                    emit(DataState.Error("${e.message}"))
                }
            }
        }
    }.flowOn(Dispatchers.IO)


    suspend fun insertPopularItem(exchangeRate: ExchangeRate) = withContext(Dispatchers.IO) {
        try {
            val exchangeRateEntity = if (exchangeRate.selected) {
                exchangeRate.toRateEntity(false)
            } else {
                exchangeRate.toRateEntity(true)
            }
            exchangeDao.updateRateEntity(exchangeRateEntity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getAllRatesByFlag(): Flow<List<ExchangeRate>> = flow {
        try {
            val flag = sharedPreferences.getInt(FLAG, SortedState.ALPHABET_ACS.value)
            val dbData = exchangeDao.getAllRatesByFlag(flag)
            emit(dbData.toExchangeRate())
        } catch (e: Exception) {
            e.printStackTrace()
            emit(listOf())
        }
    }.flowOn(Dispatchers.IO)

    fun getFavoriteRatesByFlag(): Flow<List<ExchangeRate>> = flow {
        try {
            val flag = sharedPreferences.getInt(FLAG, SortedState.ALPHABET_ACS.value)
            val dbData = exchangeDao.getAllFavoriteRatesByFlag(flag)
            emit(dbData.toExchangeRate())
        } catch (e: Exception) {
            e.printStackTrace()
            emit(listOf())
        }
    }.flowOn(Dispatchers.IO)


    fun insertFlag(sortedState: SortedState) {
        sharedPreferences.edit {
            putInt(FLAG, sortedState.value)
        }
    }

    fun insertCurrency(currency: String) {
        sharedPreferences.edit {
            putString(CURRENCY, currency)
        }
    }

    fun readCurrency() = sharedPreferences.getString(CURRENCY, "USD") ?: "USD"

    fun readFlag() = flow {
        val flag = sharedPreferences.getInt(FLAG, SortedState.ALPHABET_ACS.value)
        emit(flag)
    }

}