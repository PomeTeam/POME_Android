package com.teampome.pome.util.token

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(private val context: Context) {
    companion object {
        private val USER_ID_KEY = stringPreferencesKey("pome_user_id")
        private val USER_NICKNAME_KEY = stringPreferencesKey("pome_user_nickname")
    }

    fun getUser(): Flow<List<String?>> {
        return context.dataStore.data.map { preferences ->
            listOf(
                preferences[USER_ID_KEY],
                preferences[USER_NICKNAME_KEY]
            )
        }
    }

    suspend fun saveUser(userId: String, userNickname: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
            preferences[USER_NICKNAME_KEY] = userNickname
        }
    }

    suspend fun deleteUser() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
            preferences.remove(USER_NICKNAME_KEY)
        }
    }
}