package cz.utb.fai.ap7mt.moviesapp.network

import com.squareup.moshi.Json

data class Movie (
    @Json(name = "Title") val title: String,
    @Json(name = "Year") val year: String,
    @Json(name = "Released") val released: String,
    @Json(name = "Runtime") val runtime: String,
    @Json(name = "Director") val director: String
        )