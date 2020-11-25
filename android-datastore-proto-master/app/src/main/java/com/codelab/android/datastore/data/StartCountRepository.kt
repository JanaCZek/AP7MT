package com.codelab.android.datastore.data

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesView
import androidx.lifecycle.asLiveData
import com.codelab.android.datastore.StartCount
import com.codelab.android.datastore.UserPreferences
import kotlinx.coroutines.flow.*
import java.io.IOException

private const val START_COUNT_NAME = "start_count"

class StartCountRepository private constructor(context: Context){

    private val TAG: String = "StartCountRepo"

    private val dataStore: DataStore<StartCount> = context.createDataStore(
        fileName = "user_prefs.pb",
        serializer = StartCountSerializer
    )

    val startCountFlow: Flow<StartCount> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading sort order preferences.", exception)
                emit(StartCount.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun incrementStartCount(){
        dataStore.updateData { startCount ->
            startCount.toBuilder().setValue(startCount.value + 1).build()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StartCountRepository? = null

        fun getInstance(context: Context): StartCountRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = StartCountRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}