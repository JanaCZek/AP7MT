package cz.utb.fai.ap7mt.moviesapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieDetailViewModel(
        title: String,
        year: String,
        director: String,
        runtime: String,
        released: String,
        plot: String,
        errorMessage: String?
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _year = MutableLiveData<String?>()
    val year: LiveData<String?>
        get() = _year

    private val _director = MutableLiveData<String?>()
    val director: LiveData<String?>
        get() = _director

    private val _runtime = MutableLiveData<String?>()
    val runtime: LiveData<String?>
        get() = _runtime

    private val _released = MutableLiveData<String?>()
    val released: LiveData<String?>
        get() = _released

    private val _plot = MutableLiveData<String?>()
    val plot: LiveData<String?>
        get() = _plot

    init {
        _title.value = title
        _year.value = year
        _director.value = director
        _runtime.value = runtime
        _released.value = released
        _plot.value = plot
        _errorMessage.value = errorMessage
    }
}