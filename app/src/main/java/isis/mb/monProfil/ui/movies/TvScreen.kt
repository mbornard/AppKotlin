package isis.mb.monProfil.ui.movies

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
fun TvDetailsScreen(navController: NavController, Tvid: String?) {
    val Tvid = checkNotNull(Integer.parseInt(Tvid))
    val viewmodel: MainViewModel = viewModel()
    //Text(text = "le numero de la série est " +Tvid)

    LaunchedEffect(true){
        viewmodel.getThisTv(Tvid)
    }
    var thisTV = viewmodel.thisTv.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Détails de la série") },
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
                    painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500/${thisTV.value.poster_path}"),
                    contentDescription = thisTV.value.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    contentScale = ContentScale.Crop
                )

            }

            item {
                // Titre de la série
                Text(
                    text = thisTV.value.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                // Date de sortie
                Text(
                    text = "Date de sortie: ${thisTV.value.first_air_date}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                // Date de dernière sortie
                Text(
                    text = "Date de la dernière sortie: ${thisTV.value.last_air_date}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            if (!thisTV.value.in_production){
                item {
                    Text(
                        text = "La série est terminée",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            else{
                item {
                    Text(
                        text = "La série est en cours",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            item {
                //Nombre de saisons
                Text(
                    text = "Nombre de saisons: ${thisTV.value.number_of_seasons}" ,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item{
                //nombre d'épisodes
                Text(
                    text = "Nombre d'épisodes: ${thisTV.value.number_of_episodes}" ,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                // Note moyenne
                Text(
                    text = "Note moyenne: ${thisTV.value.vote_average}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item {
                // Synopsis de la série
                Text(
                    text = "Synopsis: ${thisTV.value.overview}",
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
            item { Text(
                text = "Distribution",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
            )
            }

            item{
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp)
                        .padding(16.dp),
                ) {

                    items(thisTV.value.credits.cast) { actor ->
                        Column {

                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w500/" + actor.profile_path,
                                contentDescription = "image du film",
                                modifier = Modifier
                                    .padding(16.dp, 50.dp, 0.dp, 0.dp)
                                    .size(200.dp)
                            )
                            Text(
                                text = actor.name,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(100.dp)
                                    .clickable {  navController.navigate("ActorScreen/${actor.id}")},
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

