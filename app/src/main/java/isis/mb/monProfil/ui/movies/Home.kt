package isis.mb.monProfil.ui.movies

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import isis.mb.monProfil.R
import isis.mb.monProfil.ui.models.Movie
import isis.mb.monProfil.ui.models.Person
import isis.mb.monProfil.ui.models.Tv
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Home(navController: NavController, classes: WindowSizeClass) {

        ScaffoldHome(navController = navController, classes)


    }
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldHome( navController: NavController, classes: WindowSizeClass) {
    val viewmodel: MainViewModel = viewModel() // get the view model   NB : pas création mais injection
    var presses by remember { mutableStateOf(0) }
    var queryString by remember { mutableStateOf("") } // the string entered by the user in the search bar

    // Launch the function to search for movies, tv and person according to the query string
    LaunchedEffect(true){ viewmodel.getSearchMovie(queryString) }
    LaunchedEffect(true){viewmodel.getSearchTv(queryString) }
    LaunchedEffect(true){viewmodel.getSearchPerson(queryString) }

    // get the list of movies, tv and person according to the search
    var searchMovies = viewmodel.MoviesSearch.collectAsState()
    var searchPerson = viewmodel.PersonsSearch.collectAsState()
    var searchTV = viewmodel.TvsSearch.collectAsState()

    // get the list of trending movies, tv and person
    var lastMovies = viewmodel.LastMovies.collectAsState()
    var lastTvs = viewmodel.LastTvs.collectAsState()
    var lastPersons = viewmodel.lastPersons.collectAsState()

    // boolean to know which screen is selected
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

    //val contextForToast = LocalContext.current.applicationContext
    var isSearchBarActive by remember { mutableStateOf(false) } // boolean called to display the search bar or not
    if(classes.widthSizeClass  != WindowWidthSizeClass.Compact){
    Row {
        //The left navigation bar
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier= Modifier.background( Color(224 , 169 , 109) ).fillMaxHeight()
        ) {
            Box(modifier = Modifier
                .clickable {
                    movieScreenSelected = true; tvScreenSelected =
                    false; actorScreenSelected = false; tvSearchSelected =
                    false; personSearchSelected = false; movieSearchSelected = false
                }
                .padding(0.dp, 25.dp, 5.dp, 25.dp)){
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painterResource(R.drawable.movie),
                        contentDescription = "icon movie"
                    )
                    Text("Movies")
                }

            }
            Box(modifier = Modifier
                .clickable {
                    movieScreenSelected = false; tvScreenSelected =
                    true; actorScreenSelected = false; movieSearchSelected =
                    false; tvSearchSelected = false; personSearchSelected = false
                }
                .padding(0.dp, 25.dp, 5.dp, 25.dp)){
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Image(
                        painterResource(R.drawable.tv),
                        contentDescription = "tv icon"
                    )
                    Text("Series")
                }
            }
            Box(modifier = Modifier
                .clickable {
                    movieScreenSelected = false; tvScreenSelected =
                    false; actorScreenSelected = true; movieSearchSelected =
                    false; tvSearchSelected = false; personSearchSelected = false
                }
                .padding(0.dp, 25.dp, 5.dp, 25.dp)){
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Image(
                        painterResource(R.drawable.person),
                        contentDescription = "icon person"
                    )
                    Text("Actors")
                }
            }
        }



        Box (modifier = Modifier.background(Color(221 , 195 , 165)).fillMaxSize()){


            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                modifier = Modifier.padding(16.dp),

            ) {

                //select the screen according to the navigation item selected
                if (movieScreenSelected) {
                    items(lastMovies.value) { film ->
                        MovieDisplay(navController = navController, film = film, classes)
                    }
                }
                if (tvScreenSelected) {
                    items(lastTvs.value) { tv ->
                        TvDisplay(navController = navController, tv = tv, classes)
                    }
                }
                if (actorScreenSelected) {
                    items(lastPersons.value) { person ->
                        PersonDisplay(navController = navController, person = person, classes)
                    }
                }
                if (movieSearchSelected) {
                    items(searchMovies.value) { film ->
                        MovieDisplay(navController = navController, film = film, classes)
                    }
                }
                if (tvSearchSelected) {
                    items(searchTV.value) { tv ->
                        TvDisplay(navController = navController, tv = tv, classes)
                    }
                }
                if (personSearchSelected) {
                    items(searchPerson.value) { person ->
                        PersonDisplay(navController = navController, person = person, classes)
                    }
                }

            }
            // if the search bar is not active and the screen is not compact, display the floating action button in the bottom right corner
            if (!isSearchBarActive && classes.widthSizeClass != WindowWidthSizeClass.Compact) {
                FloatingActionButton(
                    onClick = { isSearchBarActive = true },
                    modifier = Modifier
                        .padding(16.dp) // Ajustez la marge selon vos besoins
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }

        }


    }

}else {
    Scaffold(
        topBar = {
            if (!isSearchBarActive) { // if the search bar is not active, display the top app bar

                CenterAlignedTopAppBar(
                    colors = topAppBarColors( // Color(48, 49, 52)
                        //containerColor = MaterialTheme.colorScheme.primaryContainer,
                        //titleContentColor = MaterialTheme.colorScheme.primary,
                        containerColor =Color(224 , 169 , 109),

                        titleContentColor = Color.White,
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
                                    isSearchBarActive = true // if the user click on the search icon, the search bar is active
                                }

                        )
                    },
                    modifier = Modifier.shadow(8.dp)
                )
            }


        },

        bottomBar = {
            var selectedItem by remember { mutableStateOf(0) }

            BottomNavigation(backgroundColor = Color(224 , 169 , 109), modifier = Modifier.shadow(8.dp))
            {
                BottomNavigationItem(
                    icon = {
                        Image(
                            painterResource(R.drawable.movie),
                            contentDescription = "icon movie"
                        )
                    },
                    label = { Text("Films") },
                    selected = selectedItem == 1,
                    onClick = {
                        movieScreenSelected = true; tvScreenSelected =
                        false; actorScreenSelected = false; selectedItem = 1; tvSearchSelected =
                        false; personSearchSelected = false; movieSearchSelected = false
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Image(
                            painterResource(R.drawable.tv),
                            contentDescription = "tv icon"
                        )
                    },
                    label = { Text("Séries") },
                    selected = selectedItem == 2,
                    onClick = {
                        movieScreenSelected = false; tvScreenSelected =
                        true; actorScreenSelected = false; selectedItem =
                        2; movieSearchSelected = false; tvSearchSelected =
                        false; personSearchSelected = false
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Image(
                            painterResource(R.drawable.person),
                            contentDescription = "icon person"
                        )
                    },
                    label = { Text("Acteurs") },
                    selected = selectedItem == 3,
                    onClick = {
                        movieScreenSelected = false; tvScreenSelected =
                        false; actorScreenSelected = true; selectedItem =
                        3; movieSearchSelected = false; tvSearchSelected =
                        false; personSearchSelected = false
                    }
                )

            }

        },
        /*
        floatingActionButton = {}
        }*/
    ) { innerPadding -> // display the list of TV, movie or person according to the boolean selected by the user in the bottom nav bar
        if (movieScreenSelected) {
            Text(
                text = "Les films en tendances",
                fontSize = 27.sp,
                modifier = Modifier.padding(innerPadding),
                textAlign = TextAlign.Center,
            )
            movieScreen(lastMovies.value, innerPadding, navController, classes)
        }
        if (tvScreenSelected) {
            Text(
                text = "Les séries en tendances",
                fontSize = 27.sp,
                modifier = Modifier.padding(innerPadding),
                textAlign = TextAlign.Center
            )
            tvScreen(lastTvs.value, innerPadding, navController, classes)
        }
        if (actorScreenSelected) {
            Text(
                text = "Les acteurs en tendances",
                fontSize = 27.sp,
                modifier = Modifier.padding(innerPadding),
                textAlign = TextAlign.Center
            )
            ActorScreen(lastPersons.value, innerPadding, navController, classes)
        }
        if (movieSearchSelected) {
            Text(
                text = "Résultat de la recherche",
                fontSize = 27.sp,
                modifier = Modifier.padding(innerPadding),
                textAlign = TextAlign.Center
            )
            movieScreen(searchMovies.value, innerPadding, navController, classes)
        }
        if (tvSearchSelected) {
            Text(
                text = "Résultat de la recherche",
                fontSize = 27.sp,
                modifier = Modifier.padding(innerPadding),
                textAlign = TextAlign.Center
            )
            tvScreen(searchTV.value, innerPadding, navController, classes)
        }
        if (personSearchSelected) {
            Text(
                text = "Résultat de la recherche",
                fontSize = 27.sp,
                modifier = Modifier.padding(innerPadding),
                textAlign = TextAlign.Center
            )
            ActorScreen(searchPerson.value, innerPadding, navController, classes)
        }

    }
}
    if(isSearchBarActive) {
    //if the boolean is true, display the search bar
        SearchBar(
            modifier =  if (classes.widthSizeClass != WindowWidthSizeClass.Compact)  Modifier.fillMaxWidth() //we choose the modifier according to the screen size
            else Modifier.fillMaxWidth().background(Color(224 , 169 , 109)).padding(8.dp),
            query = queryString,
            onQueryChange = { newQueryString ->
                // this is called every time the user enters a new character
                queryString = newQueryString
            },
            onSearch = {
                // this is called when the user taps on the Search icon on the keyboard
                isActive = false
                //Toast.makeText(contextForToast, "Your query string: $queryString", Toast.LENGTH_SHORT).show()
                if (movieScreenSelected) { //if the user is on the movie screen, search for movies
                    viewmodel.getSearchMovie(queryString)
                    movieScreenSelected = false
                    movieSearchSelected = true
                    isSearchBarActive = false
                    queryString = ""
                    //Log.v("bornard", "recherche :" + searchMovies.value.size)
                }
                if (tvScreenSelected) { //if the user is on the tv screen, search for tv
                    viewmodel.getSearchTv(queryString)
                    tvScreenSelected = false
                    tvSearchSelected = true
                    isSearchBarActive = false
                    queryString = ""
                }
                if (actorScreenSelected) { // if the user is on the actor screen, search for actors
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
        ) {

        }
    }
}

@Composable //function to display the list of trending movies in the home
fun movieScreen (lastMovies: List<Movie>, innerPadding: PaddingValues, navController: NavController, classes: WindowSizeClass) {
//Box(modifier = innerPadding)

        //if(classes.widthSizeClass  == WindowWidthSizeClass.Compact) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color(221 , 195 , 165))
                    .fillMaxSize(),
            ) {

                items(lastMovies) { film ->
                    //  Log.v("bornard", "longueur :" + lastMovies.value.size)
                    MovieDisplay(navController = navController, film = film, classes)
                }
            }
       /* }else {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                modifier = Modifier
                    .padding(innerPadding),
            ) {
                items(lastMovies) { film ->
                    //  Log.v("bornard", "longueur :" + lastMovies.value.size)
                    MovieDisplay(navController = navController, film = film, classes)
                }
            }
        }*/


}

@Composable //function to display the card of trending tv in the home screen
fun tvScreen (lastTvs: List<Tv>, innerPadding: PaddingValues, navController: NavController, classes: WindowSizeClass) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(innerPadding)
            .background(Color(221 , 195 , 165))
            .fillMaxSize(),

        ) {

        items(lastTvs) { tv ->
            //  Log.v("bornard", "longueur :" + lastTvs.value.size)
            TvDisplay(navController = navController, tv = tv, classes)

        }
    }
}

@Composable //function to display the list of trending people in the home
fun ActorScreen (lastPersons: List<Person>, innerPadding: PaddingValues, navController: NavController, classes: WindowSizeClass) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(innerPadding)
            .background(Color(221 , 195 , 165))
            .fillMaxSize(),

    ) {
        //Log.v("bornard", "longueur :" + lastPersons.size)

        items(lastPersons) { person ->
            //  Log.v("bornard", "longueur :" + lastPersons.value.size)
            PersonDisplay(navController = navController, person = person, classes)
        }
    }
}

@Composable //function to display the card of the movie in the home screen
fun MovieDisplay(navController: NavController, film: Movie, classes: WindowSizeClass){
    var myPaddingTop = if(classes.widthSizeClass  != WindowWidthSizeClass.Compact)  0.dp else 38.dp

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 200.dp, height = 350.dp)
            .padding(start = 8.dp, top = myPaddingTop, end = 8.dp, bottom = 0.dp)
            .fillMaxSize()
    ) {
        Box(modifier =
        Modifier
            .fillMaxSize()
            .clickable { navController.navigate("movieScreen/${film.id}") }
            .background(color =  Color(32 , 30 , 32)),
        )
        {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/" + film.poster_path,
                    contentDescription = "image du film",
                    modifier = Modifier
                        //.padding(16.dp, 50.dp, 0.dp, 0.dp)
                        //.align(Alignment.TopCenter)
                        .size(200.dp)
                        .padding(start = 0.dp, top = 5.dp, end = 0.dp, bottom = 0.dp)
                )
                Text(
                    text = film.original_title,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp),
                    textAlign = TextAlign.Center,
                    fontSize = if (film.original_title.length< 20 ) 20.sp else 16.sp, //if the title is too long, reduce the size of the text
                    color = Color(221, 195, 165)
                    //.align(Alignment.Center),
                    //.size(200.dp),
                    //.clickable { navController.navigate("movieScreen/${film.id}") },

                )
                if (film.release_date.isNotBlank()) {
                    Text(
                        text = formatDate(
                            film.release_date,
                            "yyyy-MM-dd",
                            "dd MMM yyyy",
                            Locale.FRANCE
                        ),
                        modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = Color(221, 195, 165)
                    )
                }
            }

        }
    }
}

@Composable //function to display the card of the tv in the home screen
fun TvDisplay(navController: NavController, tv: Tv, classes: WindowSizeClass){
    var myPaddingTop = if(classes.widthSizeClass  != WindowWidthSizeClass.Compact)  0.dp else 38.dp

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 200.dp, height = 350.dp)
            .padding(start = 8.dp, top = myPaddingTop, end = 8.dp, bottom = 0.dp)
            .fillMaxSize(),
    ) {
        Box(modifier =
        Modifier
            .fillMaxSize()
            .clickable { navController.navigate("TvScreen/${tv.id}") }
            .background(color =  Color(32 , 30 , 32)),
        )
        {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {

                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/" + tv.poster_path,
                    contentDescription = "image de la série",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(start = 0.dp, top = 5.dp, end = 0.dp, bottom = 0.dp)

                )
                Text(
                    text = tv.original_name,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp) ,
                    //.clickable { navController.navigate("TvScreen/${tv.id}") },
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color(221 , 195 , 165)

                )
                Text(text =  formatDate(
                    tv.first_air_date,
                    "yyyy-MM-dd",
                    "dd MMM yyyy",
                    Locale.FRANCE
                ), modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp), textAlign = TextAlign.Center, fontSize = 12.sp, color = Color(221 , 195 , 165))


            }
        }


    }
}


@Composable //function to display the card of the people in the home screen
fun PersonDisplay(navController: NavController, person : Person, classes: WindowSizeClass){
    var myPaddingTop = if(classes.widthSizeClass  != WindowWidthSizeClass.Compact)  0.dp else 38.dp

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 200.dp, height = 350.dp)
            .padding(start = 8.dp, top = myPaddingTop, end = 8.dp, bottom = 0.dp)
            .fillMaxSize(),
    ) {
        Box(modifier =
        Modifier
            .fillMaxSize()
            .clickable { navController.navigate("ActorScreen/${person.id}") }
            .background(color =  Color(32 , 30 , 32))
        )
        {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {

                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/" + person.profile_path,
                    contentDescription = "image de la personne",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(start = 0.dp, top = 5.dp, end = 0.dp, bottom = 0.dp)

                )
                Text(
                    text = person.name,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp) ,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color(221 , 195 , 165)

                )
            }
        }
    }
}

//function to format the date
fun formatDate(
    inputDate: String,
    inputDateFormat: String,
    outputDateFormat: String,
    locale: Locale
): String {
    val inputFormat = SimpleDateFormat(inputDateFormat, locale)
    val outputFormat = SimpleDateFormat(outputDateFormat, locale)
    Log.d("date", inputDate)
    Log.d("date", inputFormat.toString())
    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date)
}
