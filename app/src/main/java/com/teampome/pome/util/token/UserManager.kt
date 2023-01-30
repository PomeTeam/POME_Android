package com.teampome.pome.util.token

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(private val context: Context) {
    companion object {
        private val USER_ID_KEY = stringPreferencesKey("pome_user_id")
        private val USER_NICKNAME_KEY = stringPreferencesKey("pome_user_nickname")
        private val USER_PHONE_KEY = stringPreferencesKey("pome_user_phone")
        private val USER_PROFILE_PRE_URL_KEY = stringPreferencesKey("pome_profile_pre_url")
        private val USER_PROFILE_IMAGE_ID = stringPreferencesKey("pome_profile_image_id")
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

    fun getUserPhone(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_PHONE_KEY]
        }
    }

    suspend fun saveUserPhone(userPhone: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_PHONE_KEY] = userPhone
        }
    }

    suspend fun deleteUserPhone() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_PHONE_KEY)
        }
    }

    fun getUserProfileUrl(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_PROFILE_PRE_URL_KEY]
        }
    }

    suspend fun saveUserProfileUrl(url: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_PROFILE_PRE_URL_KEY] = url
        }
    }

    suspend fun deleteUserProfileUrl() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_PROFILE_PRE_URL_KEY)
        }
    }

    fun getProfileKey() : Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_PROFILE_IMAGE_ID]
        }
    }

    suspend fun saveProfileKey(id: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_PROFILE_IMAGE_ID] = id
        }
    }

    suspend fun deleteProfileKey() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_PROFILE_IMAGE_ID)
        }
    }
}