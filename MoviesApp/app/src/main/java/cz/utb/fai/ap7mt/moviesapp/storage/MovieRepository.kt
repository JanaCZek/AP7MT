package cz.utb.fai.ap7mt.moviesapp.storage

import android.util.Log
import android.widget.Toast
import cz.utb.fai.ap7mt.moviesapp.network.Movie
import cz.utb.fai.ap7mt.moviesapp.network.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository(private val database: MovieDatabase) {

    suspend fun getMovies() : List<Movie> {
        val output = withContext(Dispatchers.IO) {
            val movies = database.movieDao().getAllMovies()

            return@withContext movies.map {
                Movie(
                    title = it.title,
                    year = it.year,
                    director = it.director,
                    released = it.released,
                    runtime = it.runtime,
                    id = it.id,
                    plot = it.plot,
                    response = ""
                )
            }
        }
        return output
    }

    suspend fun insert(movie: Movie) {
        withContext(Dispatchers.IO) {
            val movieEntity = MovieEntity(
                id = movie.id,
                title = movie.title,
                year = movie.year,
                director = movie.director,
                released = movie.released,
                runtime = movie.runtime,
                plot = movie.plot
            )

            database.movieDao().insertMovie(movieEntity)
        }
    }

    suspend fun getMovieByTitle(title: String) : Movie? {
        val movie = withContext(Dispatchers.IO) {
            var movieEntity = database.movieDao().getMovieByTitle(title)

            if (movieEntity == null)
                return@withContext null
            else {
                movieEntity = requireNotNull(movieEntity)

                return@withContext Movie(
                    title = movieEntity.title,
                    year = movieEntity.year,
                    director = movieEntity.director,
                    released = movieEntity.released,
                    runtime = movieEntity.runtime,
                    id = movieEntity.id,
                    plot = movieEntity.plot,
                    response = ""
                )
            }
        }

        return movie
    }

    suspend fun getMovieByTitleAndYear(title: String, year: String) : Movie? {
        val movie = withContext(Dispatchers.IO) {
            var movieEntity = database.movieDao().getMovieByTitleAndYear(title, year)

            if (movieEntity == null)
                return@withContext null
            else {
                movieEntity = requireNotNull(movieEntity)

                return@withContext Movie(
                    title = movieEntity.title,
                    year = movieEntity.year,
                    director = movieEntity.director,
                    released = movieEntity.released,
                    runtime = movieEntity.runtime,
                    id = movieEntity.id,
                    plot = movieEntity.plot,
                    response = ""
                )
            }
        }
        return movie
    }
}