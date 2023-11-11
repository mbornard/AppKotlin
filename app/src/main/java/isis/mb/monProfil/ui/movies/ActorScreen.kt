package isis.mb.monProfil.ui.movies

import android.annotation.SuppressLint
import android.media.Image
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import isis.mb.monProfil.ui.models.MainViewModel
import isis.mb.monProfil.ui.models.Person


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorDetailsScreen(navController: NavController, personid: String?, classes: WindowSizeClass) {
    val personid = checkNotNull(Integer.parseInt(personid))
    val viewmodel: MainViewModel = viewModel()

    LaunchedEffect(true) {
        viewmodel.getThisPerson(personid)
    }
    var thisPerson = viewmodel.thisPerson.collectAsState()

    //Text(text = "le numero de l'acteur est " +personid)
    //Text(text = "le nom de l'acteur est " + thisPerson.value.name)


    if (classes.widthSizeClass != WindowWidthSizeClass.Compact) {
        Row {
            displayImage(thisPerson, classes)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())

            ) {
                displayInfo(thisPerson, navController)
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            displayImage(thisPerson, classes)
            displayInfo(thisPerson,  navController)

        }

    }


}
@Composable
fun displayImage(thisPerson: State<Person>, classes: WindowSizeClass){
    //Choose the modifier according to the window size
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
    //Person's picture
    Image(
        painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500/${thisPerson.value.profile_path}"),
        contentDescription = "image de la personne",
        modifier = myModifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun displayInfo(thisPerson: State<Person>, navController: NavController){
    var isExpanded by remember { mutableStateOf(false) }

    // Person's name
    Text(
        text = thisPerson.value.name,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    // Person's birthday
    Text(
        text = "Né.e le: ${thisPerson.value.birthday}",
        fontSize = 16.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    if (thisPerson.value.deathday != null) {
        // Person's deathday if it exist
        Text(
            text = "Décédé.e le: ${thisPerson.value.deathday}",
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }

    // Bio
    if (thisPerson.value.biography.isBlank()) { // if no biography available
        Text(
            text = "Biographie : Aucune biographie disponible",
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    } else {
        // if biography size is big, display the first line of the biography
        val previewText = if (thisPerson.value.biography.lines().size > 1) {
            thisPerson.value.biography.lines().take(1).joinToString("\n")
        } else { //else display the original biography
            thisPerson.value.biography
        }

        Text(
            text = if (isExpanded) thisPerson.value.biography else previewText,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // button "Voir plus" to display the entire biography or "voir moins" to reduce the size of the biography
        if (thisPerson.value.biography.lines().size > 1) {
            TextButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(if (isExpanded) "Voir moins" else "Voir plus")
            }
        }
    }
    Text(
        text = "Filmographie :",
        fontSize = 27.sp,
        fontWeight = FontWeight.Bold,
    )
    // display the list of the movies done by this person
    LazyHorizontalGrid(rows = GridCells.Fixed(1), modifier = Modifier.fillMaxWidth().height(400.dp).padding(16.dp)){
        items(thisPerson.value.credits.cast){ movie ->
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
                    .clickable { navController.navigate("MovieScreen/${movie.id}") })
                {
                    Column (horizontalAlignment = Alignment.CenterHorizontally) {

                        AsyncImage(
                            model =  "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                            contentDescription = "image du film",
                            modifier = Modifier
                                //.clip(RoundedCornerShape(12.dp))
                                .size(200.dp)
                                .padding(
                                    start = 0.dp,
                                    top = 5.dp,
                                    end = 0.dp,
                                    bottom = 0.dp
                                ),
                            //contentScale = ContentScale.Crop
                        )
                        Text(
                            text = "${movie.original_title}",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp) ,
                        )
                    }
                }

            }


        }
    }

}

