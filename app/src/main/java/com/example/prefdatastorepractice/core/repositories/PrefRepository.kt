package com.example.prefdatastorepractice.core.repositories

import kotlinx.coroutines.flow.Flow

interface PrefRepository {
    fun getString(key: String, defaultValue: String): Flow<String>
    suspend fun saveString(key: String, value: String)

    fun getInt(key: String, defaultValue: Int): Flow<Int>
    suspend fun saveInt(key: String, value: Int)

    fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean>
    suspend fun saveBoolean(key: String, value: Boolean)

    fun getLong(key: String, defaultValue: Long): Flow<Long>
    suspend fun saveLong(key: String, value: Long)

    fun getDouble(key: String, defaultValue: Double): Flow<Double>
    suspend fun saveDouble(key: String, value: Double)
}