package cz.utb.fai.ap7mt.moviesapp.storage

import cz.utb.fai.ap7mt.moviesapp.network.Movie

class MovieRepository(private val database: MovieDatabase) {

    fun getMovies() : List<Movie> {
        val movies = database.movieDao().getAllMovies()

        return movies.map {
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

    suspend fun insert(movie: Movie) {
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

    fun getMovieById(id: String) : Movie {
        val movieEntity = database.movieDao().getMovieById(id)

        return Movie(
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