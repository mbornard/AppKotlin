package isis.mb.monProfil.ui.movies

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import isis.mb.monProfil.R




@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Home(navController: NavController) {

        ScaffoldHome(navController = navController)


    }
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldHome( navController: NavController) {
    val viewmodel: MainViewModel = viewModel() //atention pas création masi injection
    var presses by remember { mutableStateOf(0) }

    var movieScreenSelected by remember { mutableStateOf(true) }
    var tvScreenSelected by remember { mutableStateOf(false) }
    var actorScreenSelected by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Top app bar 2")
                }
            )
        },

        /*
        *colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
        * */

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
                    onClick = { movieScreenSelected = true; tvScreenSelected = false; actorScreenSelected = false; selectedItem = 1 }
                )
                BottomNavigationItem(
                    icon = { Image(
                        painterResource(R.drawable.tv),
                        contentDescription = "tv icon"
                    ) },
                    label = { Text("Séries") },
                    selected = selectedItem == 2,
                    onClick = { movieScreenSelected = false; tvScreenSelected = true; actorScreenSelected = false; selectedItem = 2 }
                )
                BottomNavigationItem(
                    icon = { Image(
                        painterResource(R.drawable.person),
                        contentDescription = "icon person"
                    ) },
                    label = { Text("Acteurs") },
                    selected = selectedItem == 3,
                    onClick = { movieScreenSelected = false; tvScreenSelected = false; actorScreenSelected = true; selectedItem = 3 }
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
            movieScreen(viewmodel, innerPadding, navController)
        }
        if (tvScreenSelected) {
            tvScreen(viewmodel, innerPadding, navController)
        }
        if (actorScreenSelected) {
            ActorScreen(viewmodel, innerPadding, navController)
        }


    }
}

@Composable
fun movieScreen (viewModel: MainViewModel, innerPadding: PaddingValues, navController: NavController) {
    var lastMovies = viewModel.LastMovies.collectAsState()

//Box(modifier = innerPadding)
    Text(
        text = "Les films en tendances",
        fontSize = 27.sp,
        modifier = Modifier.padding(innerPadding),
        textAlign = TextAlign.Center
    )
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier = Modifier
            .padding(innerPadding),
    ) {

        items(lastMovies.value) { film ->
            //  Log.v("bornard", "longueur :" + lastMovies.value.size)
            Column {

                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/" + film.poster_path,
                    contentDescription = "image du film",
                    modifier = Modifier
                        .padding(16.dp, 50.dp, 0.dp, 0.dp)
                        .size(200.dp)
                )
                Text(
                    text = film.original_title,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp)
                        .clickable {  navController.navigate("movieScreen/${film.id}")},
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center

                )
            }

        }

    }


}

@Composable
fun tvScreen (viewModel: MainViewModel, innerPadding: PaddingValues, navController: NavController) {
    var lastTvs = viewModel.LastTvs.collectAsState()
    Text(
        text = "Les séries en tendances",
        fontSize = 27.sp,
        modifier = Modifier.padding(innerPadding),
        textAlign = TextAlign.Center
    )
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier = Modifier
            .padding(innerPadding),
    ) {

        items(lastTvs.value) { tv ->
            //  Log.v("bornard", "longueur :" + lastTvs.value.size)
            Column {

                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/" + tv.poster_path,
                    contentDescription = "image du film",
                    modifier = Modifier
                        .padding(16.dp, 50.dp, 0.dp, 0.dp)
                        .size(200.dp)
                )
                Text(
                    text = tv.original_name,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp)
                        .clickable {  navController.navigate("TvScreen/${tv.id}")},


                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }


        }

    }
}

@Composable
fun ActorScreen (viewModel: MainViewModel, innerPadding: PaddingValues, navController: NavController) {
    var lastPersons = viewModel.lastPersons.collectAsState()
    Text(
        text = "Les acteurs en tendances",
        fontSize = 27.sp,
        modifier = Modifier.padding(innerPadding),
        textAlign = TextAlign.Center
    )
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier = Modifier
            .padding(innerPadding),
    ) {
        Log.v("bornard", "longueur :" + lastPersons.value.size)

        items(lastPersons.value) { person ->
            Column {

                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/" + person.profile_path,
                    contentDescription = "image du film",
                    modifier = Modifier
                        .padding(16.dp, 50.dp, 0.dp, 0.dp)
                        .size(200.dp)
                )
                Text(
                    text = person.name,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp)
                        .clickable {  navController.navigate("ActorScreen/${person.id}")},

                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}