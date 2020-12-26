package cz.utb.fai.ap7mt.moviesapp.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MovieEntity::class), version = 1, exportSchema = false)
public abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}

@Volatile
private lateinit var _instance: MovieDatabase

fun getDatabase(context: Context) : MovieDatabase {

    synchronized(MovieDatabase::class.java) {
        if (!::_instance.isInitialized)
            _instance = Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                "movies_database"
            ).build()

    }
    return _instance
}