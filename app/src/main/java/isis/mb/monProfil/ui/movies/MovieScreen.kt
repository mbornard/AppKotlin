package isis.mb.monProfil.ui.movies
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import isis.mb.monProfil.ui.models.Movie
import isis.mb.monProfil.ui.models.MovieWithCast
import isis.mb.monProfil.ui.models.Tv


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(navController: NavController, filmid: String?, classes: WindowSizeClass) {
    val filmid = checkNotNull(Integer.parseInt(filmid))
    val viewmodel: MainViewModel = viewModel()

    LaunchedEffect(true) {
        viewmodel.getThisMovie(filmid)
    }
    var thisMovie = viewmodel.thisMovie.collectAsState()
    //Log.v("bornard", "movie" + thisMovie)
    //Text(text = "le numero du film est " +filmid)

    if (classes.widthSizeClass != WindowWidthSizeClass.Compact) {
        Row (modifier = Modifier.background(Color(221 , 195 , 165))){
            displayImage(thisMovie, classes)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())

            ) {
                displayInfo(thisMovie, navController)
            }
        }
    } else {
        Box(
            modifier = Modifier.background(Color(221, 195, 165))
        ) {// a box is used to have a background color

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())

            ) {
                displayImage(thisMovie, classes)
                displayInfo(thisMovie, navController)
            }
        }
    }

}

@Composable
fun displayImage(thisMovie: State<MovieWithCast>, classes: WindowSizeClass){

    //choix du modifier en fonctio  de la taille de l'écran
    var myModifier: Modifier
    if(classes.widthSizeClass != WindowWidthSizeClass.Compact){
        myModifier = Modifier
            .height(500.dp)
    }else{
        myModifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .clip(RoundedCornerShape(16.dp))
    }
    // Image du film
    Image(
        painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500/${thisMovie.value.poster_path}"),
        contentDescription = thisMovie.value.title,
        modifier = myModifier,

    contentScale = ContentScale.Crop
    )
}

@Composable
fun displayInfo(thisMovie: State<MovieWithCast>, navController: NavController){
    // Titre du film
    Text(
        text = thisMovie.value.title,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )


    // Date de sortie
    Text(
    text = "Date de sortie: ${thisMovie.value.release_date}",
    fontSize = 16.sp,
    modifier = Modifier.padding(vertical = 8.dp)
    )

    // Note moyenne
    Text(
    text = "Note moyenne: ${thisMovie.value.vote_average}",
    fontSize = 16.sp,
    modifier = Modifier.padding(vertical = 8.dp)
    )

    // Synopsis du film
    Text(
    text = "Synopsis: ${thisMovie.value.overview}",
    fontSize = 16.sp,
    modifier = Modifier.padding(vertical = 8.dp)
    )


    /*item {
        Text(text = "Titre "+ thisMovie.value.original_title)
        thisMovie.value.credits.cast.forEach {
            Text(text = it.name)
        }
    }*/

    Text(
    text = "Distribution",
    fontSize = 27.sp,
    fontWeight = FontWeight.Bold,
    )


    // Distribution du film
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
                Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate("ActorScreen/${actor.id}") }
                    .background(color = Color(32, 30, 32))
                )
                {
                    Column (horizontalAlignment = Alignment.CenterHorizontally) {

                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500/" + actor.profile_path,
                            contentDescription = "image de la personne",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(start = 0.dp, top = 5.dp, end = 0.dp, bottom = 0.dp)

                        )
                        Text(
                            text = actor.name,
                            modifier = Modifier
                                .padding(0.dp, 10.dp, 0.dp, 0.dp) ,
                              fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = Color(221 , 195 , 165)


                        )
                        Text(text = actor.character, fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp),                             color = Color(221 , 195 , 165)
                        )
                    }
                }

            }
        }
    }

}