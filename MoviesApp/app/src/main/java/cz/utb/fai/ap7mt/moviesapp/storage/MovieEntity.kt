package cz.utb.fai.ap7mt.moviesapp.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
class MovieEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val year: String,
    val director: String,
    val released: String,
    val runtime: String,
    val plot: String
)
