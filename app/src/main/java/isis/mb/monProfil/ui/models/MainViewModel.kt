package isis.mb.monProfil.ui.models

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import isis.mb.monProfil.ui.API.TmdbAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.math.log

class MainViewModel : ViewModel() {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build();

    val api = retrofit.create(TmdbAPI::class.java)


    val LastMovies = MutableStateFlow<List<Movie>>(listOf())
    val LastTvs = MutableStateFlow<List<Tv>>(listOf())
    val lastPersons = MutableStateFlow<List<Person>>(listOf())
    val thisMovie = MutableStateFlow(MovieWithCast())
    val thisPerson = MutableStateFlow(Person())
    val thisTv = MutableStateFlow(TvWithCast())

    init {
        getLastMovies()
        getLastTvs()
        getLastPersons()
    }

    fun getLastMovies() {
        viewModelScope.launch {
            LastMovies.value = api.lastMovies("38c913fcb4a7397ddf7afa64761bb702").results //permet d'acceder aux valeurs dans le film
            //Log.v("bornard", "longueur :" + LastMovies.value.size)
        }
    }
    fun getLastTvs() {
        viewModelScope.launch {
            LastTvs.value = api.lastTvs("38c913fcb4a7397ddf7afa64761bb702").results //permet d'acceder aux valeurs dans le film
        }
    }
    fun getLastPersons(){
        viewModelScope.launch {
            lastPersons.value = api.lastPersons("38c913fcb4a7397ddf7afa64761bb702").results
        }
    }

    fun getThisMovie(id :Int){
        viewModelScope.launch {
            thisMovie.value = api.OneMovie(id = id,"38c913fcb4a7397ddf7afa64761bb702")

        }
    }

    fun getThisPerson(id :Int){
        viewModelScope.launch {
            thisPerson.value = api.OnePerson(id = id, "38c913fcb4a7397ddf7afa64761bb702")

        }
    }
    fun getThisTv(id :Int){
        viewModelScope.launch {
            thisTv.value = api.OneTv(id = id,"38c913fcb4a7397ddf7afa64761bb702")

        }
    }
}