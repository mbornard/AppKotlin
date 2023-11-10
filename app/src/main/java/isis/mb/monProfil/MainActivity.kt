package isis.mb.monProfil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import isis.mb.monProfil.ui.movies.ActorDetailsScreen
import isis.mb.monProfil.ui.movies.Home
import isis.mb.monProfil.ui.movies.MovieDetailsScreen
import isis.mb.monProfil.ui.movies.TvDetailsScreen
import isis.mb.monProfil.ui.profil.Screen
import isis.mb.monProfil.ui.theme.MonProfilTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            MonProfilTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    //Screen(windowSizeClass)
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "screen") {
                        composable("screen") {
                            Screen(navController= navController, classes= windowSizeClass)
                        }
                        composable("Film") {
                            Home(navController= navController, windowSizeClass)
                        }
                        composable("movieScreen/{filmId}"){
                                backStackEntry ->
                            MovieDetailsScreen(navController= navController, backStackEntry.arguments?.getString("filmId"), windowSizeClass)
                        }
                        composable("TvScreen/{TvId}"){
                                backStackEntry ->
                            TvDetailsScreen(navController= navController, backStackEntry.arguments?.getString("TvId"), windowSizeClass)
                        }
                        composable("ActorScreen/{PersonId}"){
                                backStackEntry ->
                            ActorDetailsScreen(navController= navController, backStackEntry.arguments?.getString("PersonId"), windowSizeClass)
                        }
                    }


                }
            }
        }
    }
}