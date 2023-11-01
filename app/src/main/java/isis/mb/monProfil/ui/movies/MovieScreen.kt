package isis.mb.monProfil.ui.movies
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import isis.mb.monProfil.ui.models.MainViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(navController: NavController, filmid: String?) {
    val filmid = checkNotNull(Integer.parseInt(filmid))
    val viewmodel: MainViewModel = viewModel()

    LaunchedEffect(true){
        viewmodel.getThisMovie(filmid)
    }
    var thisMovie = viewmodel.thisMovie.collectAsState()
    //Log.v("bornard", "movie" + thisMovie)
    //Text(text = "le numero du film est " +filmid)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Détails du film") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                // Image du film
                Image(
                    painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500/${thisMovie.value.poster_path}"),
                    contentDescription = thisMovie.value.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    contentScale = ContentScale.Crop
                )

            }

            item {
                // Titre du film
                Text(
                    text = thisMovie.value.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                // Date de sortie
                Text(
                    text = "Date de sortie: ${thisMovie.value.release_date}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                // Note moyenne
                Text(
                    text = "Note moyenne: ${thisMovie.value.vote_average}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item {
                // Synopsis du film
                Text(
                    text = "Synopsis: ${thisMovie.value.overview}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }


            /*item {
                Text(text = "Titre "+ thisMovie.value.original_title)
                thisMovie.value.credits.cast.forEach {
                    Text(text = it.name)
                }
            }*/
            item {
                Text(
                    text = "Distribution",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            item {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(16.dp),
                ) {

                    items(thisMovie.value.credits.cast) { actor ->
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
                                .clickable { navController.navigate("ActorScreen/${actor.id}") })
                            {

                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w500/" + actor.profile_path,
                                    contentDescription = "image de la personne",
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .size(200.dp)
                                        .padding(start = 0.dp, top = 5.dp, end = 0.dp, bottom = 0.dp)

                                )
                                Text(
                                    text = actor.name,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .align(Alignment.BottomCenter)
                                        .size(100.dp),
                                       // .clickable { navController.navigate("ActorScreen/${actor.id}") },
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center

                                )
                            }

                        }
                    }
                }

            }


        }
    }
}
