package isis.mb.monProfil.ui.movies

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import isis.mb.monProfil.ui.models.MainViewModel
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import isis.mb.monProfil.R
import isis.mb.monProfil.ui.models.Movie
import isis.mb.monProfil.ui.models.Person
import isis.mb.monProfil.ui.models.Tv
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Home(navController: NavController) {

        ScaffoldHome(navController = navController)


    }
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldHome( navController: NavController) {
    val viewmodel: MainViewModel = viewModel() //atention pas création mais injection
    var presses by remember { mutableStateOf(0) }
    var queryString by remember { mutableStateOf("") }


    LaunchedEffect(true){ viewmodel.getSearchMovie(queryString) }
    LaunchedEffect(true){viewmodel.getSearchTv(queryString) }
    LaunchedEffect(true){viewmodel.getSearchPerson(queryString) }

    var searchMovies = viewmodel.MoviesSearch.collectAsState()
    var searchPerson = viewmodel.PersonsSearch.collectAsState()
    var searchTV = viewmodel.TvsSearch.collectAsState()

    var lastMovies = viewmodel.LastMovies.collectAsState()
    var lastTvs = viewmodel.LastTvs.collectAsState()
    var lastPersons = viewmodel.lastPersons.collectAsState()


    var movieScreenSelected by remember { mutableStateOf(true) }
    var tvScreenSelected by remember { mutableStateOf(false) }
    var actorScreenSelected by remember { mutableStateOf(false) }
    var movieSearchSelected by remember { mutableStateOf(false) }
    var tvSearchSelected by remember { mutableStateOf(false) }
    var personSearchSelected by remember { mutableStateOf(false) }



    // if the search bar is active or not
    var isActive by remember {
        mutableStateOf(false)
    }

    val contextForToast = LocalContext.current.applicationContext
    var isSearchBarActive by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if(isSearchBarActive) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.padding(horizontal = if (isActive) 0.dp else 8.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    query = queryString,
                    onQueryChange = { newQueryString ->
                        // this is called every time the user enters a new character
                        queryString = newQueryString
                    },
                    onSearch = {
                        // this is called when the user taps on the Search icon on the keyboard
                        isActive = false
                        Toast.makeText(contextForToast, "Your query string: $queryString", Toast.LENGTH_SHORT)
                            .show()
                        if (movieScreenSelected) {
                            viewmodel.getSearchMovie(queryString)
                            movieScreenSelected = false
                            movieSearchSelected = true
                            isSearchBarActive = false
                            queryString = ""
                            Log.v("bornard", "recherche :" + searchMovies.value.size)
                        }
                        if (tvScreenSelected) {
                            viewmodel.getSearchTv(queryString)
                            tvScreenSelected = false
                            tvSearchSelected = true
                            isSearchBarActive = false
                            queryString = ""
                        }
                        if (actorScreenSelected) {
                            viewmodel.getSearchPerson(queryString)
                            actorScreenSelected = false
                            personSearchSelected = true
                            isSearchBarActive = false
                            queryString = ""
                        }


                    },
                    active = isActive,
                    onActiveChange = { activeChange ->
                        isActive = activeChange
                    },
                    placeholder = {
                        Text(text = "Search")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                ){

                }
            }
            else {
                CenterAlignedTopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("CinéMatisse")
                    },
                    actions = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search icon",
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    isSearchBarActive = true
                                }

                        )
                    }

                )
            }
            /*
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Top app bar 2")
                }
            )
        },*/

        },

            bottomBar = {
            var selectedItem by remember { mutableStateOf(0) }

            BottomNavigation(backgroundColor = MaterialTheme.colorScheme.primaryContainer) {
                BottomNavigationItem(
                    icon = { Image(
                        painterResource(R.drawable.movie),
                        contentDescription = "icon movie"
                    ) },
                    label = { Text("Films") },
                    selected = selectedItem == 1,
                    onClick = { movieScreenSelected = true; tvScreenSelected = false; actorScreenSelected = false; selectedItem = 1 ; tvSearchSelected =false; personSearchSelected = false; movieSearchSelected = false}
                )
                BottomNavigationItem(
                    icon = { Image(
                        painterResource(R.drawable.tv),
                        contentDescription = "tv icon"
                    ) },
                    label = { Text("Séries") },
                    selected = selectedItem == 2,
                    onClick = { movieScreenSelected = false; tvScreenSelected = true; actorScreenSelected = false; selectedItem = 2; movieSearchSelected = false ; tvSearchSelected = false; personSearchSelected = false}
                )
                BottomNavigationItem(
                    icon = { Image(
                        painterResource(R.drawable.person),
                        contentDescription = "icon person"
                    ) },
                    label = { Text("Acteurs") },
                    selected = selectedItem == 3,
                    onClick = { movieScreenSelected = false; tvScreenSelected = false; actorScreenSelected = true; selectedItem = 3 ; movieSearchSelected = false ; tvSearchSelected = false; personSearchSelected = false }
                )

            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {innerPadding ->
        if (movieScreenSelected) {
            Text(text = "Les films en tendances", fontSize = 27.sp, modifier = Modifier.padding(innerPadding), textAlign = TextAlign.Center)
            movieScreen(lastMovies.value, innerPadding, navController)
        }
        if (tvScreenSelected) {
            Text(text = "Les séries en tendances", fontSize = 27.sp, modifier = Modifier.padding(innerPadding), textAlign = TextAlign.Center)
            tvScreen(lastTvs.value, innerPadding, navController)
        }
        if (actorScreenSelected) {
            Text(text = "Les acteurs en tendances", fontSize = 27.sp, modifier = Modifier.padding(innerPadding), textAlign = TextAlign.Center)
            ActorScreen(lastPersons.value, innerPadding, navController)
        }
        if(movieSearchSelected){
            Text(text = "Résultat de la recherche", fontSize = 27.sp, modifier = Modifier.padding(innerPadding), textAlign = TextAlign.Center)
            movieScreen(searchMovies.value, innerPadding, navController)
        }
        if(tvSearchSelected){
            Text(text = "Résultat de la recherche", fontSize = 27.sp, modifier = Modifier.padding(innerPadding), textAlign = TextAlign.Center)
            tvScreen(searchTV.value, innerPadding, navController)
        }
        if(personSearchSelected){
            Text(text = "Résultat de la recherche", fontSize = 27.sp, modifier = Modifier.padding(innerPadding), textAlign = TextAlign.Center)
            ActorScreen(searchPerson.value, innerPadding, navController)
        }

    }
}

@Composable
fun movieScreen (lastMovies: List<Movie>, innerPadding: PaddingValues, navController: NavController) {
//Box(modifier = innerPadding)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(innerPadding),
    ) {

        items(lastMovies) { film ->
            //  Log.v("bornard", "longueur :" + lastMovies.value.size)
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .size(width = 200.dp, height = 350.dp)
                    .padding(start = 8.dp, top = 38.dp, end = 8.dp, bottom = 0.dp)
                    .fillMaxSize(),
            ) {
                Box(modifier =
                Modifier.fillMaxSize()
                        .clickable { navController.navigate("movieScreen/${film.id}") })
                {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/" + film.poster_path,
                        contentDescription = "image du film",
                        modifier = Modifier
                            //.padding(16.dp, 50.dp, 0.dp, 0.dp)
                            .align(Alignment.TopCenter)
                            .size(200.dp)
                            .padding(start = 0.dp, top = 5.dp, end = 0.dp, bottom = 0.dp)
                    )
                    Text(
                        text = film.original_title,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomCenter)
                            .size(100.dp),
                        //.clickable { navController.navigate("movieScreen/${film.id}") },
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center

                    )
                }
            }

        }

    }


}

@Composable
fun tvScreen (lastTvs: List<Tv>, innerPadding: PaddingValues, navController: NavController) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(innerPadding),
    ) {

        items(lastTvs) { tv ->
            //  Log.v("bornard", "longueur :" + lastTvs.value.size)
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .size(width = 200.dp, height = 350.dp)
                    .padding(start = 8.dp, top = 38.dp, end = 8.dp, bottom = 0.dp)
                    .fillMaxSize(),
            ) {
                Box(modifier =
                Modifier.fillMaxSize()
                    .clickable { navController.navigate("TvScreen/${tv.id}") })
                {

                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/" + tv.poster_path,
                        contentDescription = "image du film",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .size(200.dp)
                            .padding(start = 0.dp, top = 5.dp, end = 0.dp, bottom = 0.dp)

                    )
                    Text(
                        text = tv.original_name,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomCenter)
                            .size(100.dp),
                        //.clickable { navController.navigate("TvScreen/${tv.id}") },


                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }


            }

        }
    }
}

@Composable
fun ActorScreen (lastPersons: List<Person>, innerPadding: PaddingValues, navController: NavController) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(innerPadding),
    ) {
        //Log.v("bornard", "longueur :" + lastPersons.size)

        items(lastPersons) { person ->
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .size(width = 200.dp, height = 350.dp)
                    .padding(start = 8.dp, top = 38.dp, end = 8.dp, bottom = 0.dp)
                    .fillMaxSize(),
            ) {
                Box(modifier =
                Modifier.fillMaxSize()
                    .clickable { navController.navigate("ActorScreen/${person.id}") })
                {

                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/" + person.profile_path,
                        contentDescription = "image du film",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .size(200.dp)
                            .padding(start = 0.dp, top = 5.dp, end = 0.dp, bottom = 0.dp)

                    )
                    Text(
                        text = person.name,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomCenter)
                            .size(100.dp),
                        //.clickable { navController.navigate("ActorScreen/${person.id}") },

                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
