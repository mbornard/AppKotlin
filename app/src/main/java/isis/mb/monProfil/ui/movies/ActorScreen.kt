package isis.mb.monProfil.ui.movies

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import isis.mb.monProfil.ui.models.MainViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorDetailsScreen(navController: NavController, personid: String?) {
    val personid = checkNotNull(Integer.parseInt(personid))
    val viewmodel: MainViewModel = viewModel()

    LaunchedEffect(true){
        viewmodel.getThisPerson(personid)
    }
    var thisPerson = viewmodel.thisPerson.collectAsState()

    Text(text = "le numero de l'acteur est " +personid)
    Text(text = "le nom de l'acteur est " + thisPerson.value.name)



        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Détails de la personne") },
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
                    // Image de la personne
                    Image(
                        painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500/${thisPerson.value.profile_path}"),
                        contentDescription = "image de la personne",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                item {
                    // Nom de la personne
                    Text(
                        text = thisPerson.value.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                item {
                    // Date de naissance
                    Text(
                        text = "Né.e le: ${thisPerson.value.birthday}",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                if (thisPerson.value.deathday != null) {
                    item {
                        // Date de décès
                        Text(
                            text = "Décédé.e le: ${thisPerson.value.deathday}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                item {
                    // Bio
                    Text(
                        text = "Biographie : ${thisPerson.value.biography}",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }


            }
        }
}