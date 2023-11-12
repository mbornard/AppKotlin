package isis.mb.monProfil.ui.movies

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
import isis.mb.monProfil.ui.models.TvWithCast

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvDetailsScreen(navController: NavController, Tvid: String?, classes: WindowSizeClass) {
    val Tvid = checkNotNull(Integer.parseInt(Tvid))
    val viewmodel: MainViewModel = viewModel()
    //Text(text = "le numero de la série est " +Tvid)

    LaunchedEffect(true) {
        viewmodel.getThisTv(Tvid)
    }
    var thisTV = viewmodel.thisTv.collectAsState()


    if (classes.widthSizeClass != WindowWidthSizeClass.Compact) {
        Row(modifier = Modifier.background(Color(221 , 195 , 165))) {
            displayMainInfo(thisTV, classes)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())

            ) {
                displayInfo(thisTV, navController)
            }
        }
    }else{
        Box(modifier = Modifier.background(Color(221, 195, 165))) {// a box is used to have a background color

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())

            ) {
                displayMainInfo(thisTV, classes)
                displayInfo(thisTV, navController)
            }
        }
    }
}

@Composable
fun displayMainInfo(thisTV: State<TvWithCast>, classes: WindowSizeClass){
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
    //image de la série
    Image(
        painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500/${thisTV.value.poster_path}"),
        contentDescription = thisTV.value.name,
        modifier = myModifier,
        contentScale = ContentScale.Crop
    )

}

@Composable
fun displayInfo(thisTV: State<TvWithCast>, navController: NavController){
    //titre série
    Text(
        text = thisTV.value.name,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    // Date de sortie
    Text(
        text = "Date de sortie: ${thisTV.value.first_air_date}",
        fontSize = 16.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )


    // Date de dernière sortie
    Text(
        text = "Date de la dernière sortie: ${thisTV.value.last_air_date}",
        fontSize = 16.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    if (!thisTV.value.in_production) {
        Text(
            text = "La série est terminée",
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

    } else {
        Text(
            text = "La série est en cours",
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

    }
    //Nombre de saisons
    Text(
        text = "Nombre de saisons: ${thisTV.value.number_of_seasons}",
        fontSize = 16.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    //nombre d'épisodes
    Text(
        text = "Nombre d'épisodes: ${thisTV.value.number_of_episodes}",
        fontSize = 16.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )



    // Note moyenne
    Text(
        text = "Note moyenne: ${thisTV.value.vote_average}",
        fontSize = 16.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )


    // Synopsis de la série
    Text(
        text = "Synopsis: ${thisTV.value.overview}",
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


    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(16.dp),
    ) {

        items(thisTV.value.credits.cast) { actor ->
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
                    .background(color = Color(32, 30, 32)))

                    {
                    Column (horizontalAlignment = Alignment.CenterHorizontally) {

                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500/" + actor.profile_path,
                            contentDescription = "image de la personne",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(
                                    start = 0.dp,
                                    top = 5.dp,
                                    end = 0.dp,
                                    bottom = 0.dp
                                )

                        )
                        Text(
                            text = actor.name,
                            modifier = Modifier
                                .padding(0.dp, 10.dp, 0.dp, 0.dp) ,
                            //.clickable {  navController.navigate("ActorScreen/${actor.id}")},
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


