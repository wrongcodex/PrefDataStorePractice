package com.example.prefdatastorepractice.core.db.prefDataStore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore


val Context.dataStore by preferencesDataStore("my_store")

object PrefUtils {

}