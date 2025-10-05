package com.example.prefdatastorepractice.core.repositories

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PrefRepositoryImpl(
    private val context: Context
): PrefRepository{
    private val Context.dataStore by preferencesDataStore("my_store")
    override fun getString(
        key: String,
        defaultValue: String
    ): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: defaultValue
        }
    }
    override suspend fun saveString(key: String, value: String) {
        context.dataStore.edit {preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    override fun getInt(
        key: String,
        defaultValue: Int
    ): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)] ?: defaultValue
        }
    }

    override suspend fun saveInt(key: String, value: Int) {
        context.dataStore.edit {preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    override fun getBoolean(
        key: String,
        defaultValue: Boolean
    ): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: defaultValue
        }
    }

    override suspend fun saveBoolean(key: String, value: Boolean) {
        context.dataStore.edit {preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    override fun getLong(
        key: String,
        defaultValue: Long
    ): Flow<Long> {
        return context.dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)] ?: defaultValue
        }
    }

    override suspend fun saveLong(key: String, value: Long) {
        context.dataStore.edit {preferences ->
            preferences[longPreferencesKey(key)] = value
        }
    }

    override fun getDouble(
        key: String,
        defaultValue: Double
    ): Flow<Double> {
        return context.dataStore.data.map { preferences ->
            preferences[doublePreferencesKey(key)] ?: defaultValue
        }
    }

    override suspend fun saveDouble(key: String, value: Double) {
        context.dataStore.edit {preferences ->
            preferences[doublePreferencesKey(key)] = value
        }
    }
}