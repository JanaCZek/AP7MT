package cz.utb.fai.ap7mt.moviesapp.storage

import androidx.room.Dao
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("select * from movies")
    fun getAllMovies() : List<MovieEntity>

    @Query("select * from movies where id=:id")
    fun getMovieById(id: String) : MovieEntity

    @Insert(onConflict = IGNORE)
    suspend fun insertMovie(movieEntity: MovieEntity)
}