package cz.utb.fai.ap7mt.moviesapp.storage

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import java.util.*

@Dao
interface MovieDao {
    @Query("select * from movies")
    fun getAllMovies() : List<MovieEntity>

    @Query("select * from movies where id=:id")
    fun getMovie(id: String) : MovieEntity?

    @Query("select * from movies where title=:title")
    fun getMovieByTitle(title: String) : MovieEntity?

    @Query("select * from movies where title=:title and year=:year")
    fun getMovieByTitleAndYear(title: String, year: String) : MovieEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieEntity: MovieEntity)
}