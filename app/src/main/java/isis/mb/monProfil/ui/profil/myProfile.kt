package isis.mb.monProfil.ui.profil

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import isis.mb.monProfil.R




@Composable
fun photoProfil() {
    Image(
        painterResource(R.drawable.pdp),
        contentDescription = "ma photo de profil",
        Modifier.clip(CircleShape)
    )
}
@Composable
fun monNom() {
    Text(
        text = "Bornard Matisse",
        fontWeight = FontWeight.Thin,
        fontSize = 50.sp,
        color = Color.LightGray,
        fontFamily = FontFamily.SansSerif
    )
}
@Composable
fun statut(){
    Text(
        text = "Étudiant en 4e année de l'école d'ingénieurs ISIS. \n Alternant au CHU de Toulouse",
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        color = Color.Gray,
        fontFamily = FontFamily.SansSerif,
        textAlign = TextAlign.Center
    )
}

@Composable
fun resSociaux(contenu : String , icon : Int){
    Row(){
        Image(
            painterResource(icon),
            contentDescription = "icon mail",
            //Modifier.clip(CircleShape)
        )
        Text(
            text = contenu,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            color = Color.Gray,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )

    }


}

@Composable
fun boutton(navController: NavController){
    Button(
        onClick = { navController.navigate("Film")},
        modifier = Modifier

            .padding(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = "Démarrer",
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun Screen(classes: WindowSizeClass, navController: NavController) {
    val classeHauteur = classes.heightSizeClass
    val classeLargeur = classes.widthSizeClass
    when (classeLargeur) {
        WindowWidthSizeClass.Compact-> compactScreen(navController)
        WindowWidthSizeClass.Medium -> mediumAndExpandedScreen(navController)
        WindowWidthSizeClass.Expanded -> mediumAndExpandedScreen(navController)
    }
}

@Composable
fun informations(navController: NavController){
    monNom()
    Spacer(Modifier.height(10.dp))
    statut()
    Spacer(Modifier.height(20.dp))
    resSociaux("matisse.bornard@etud.univ-jfc.fr", R.drawable.mail)
    Spacer(Modifier.height(8.dp))
    resSociaux("www.linkedin.com/in/matisse-bornard", R.drawable.badge)
    Spacer(Modifier.height(20.dp))
    boutton(navController = navController)
}
@Composable
fun compactScreen(navController: NavController){
    Column (horizontalAlignment = Alignment.CenterHorizontally ){
        Spacer(Modifier.height(15.dp))
        photoProfil()
        Spacer(Modifier.height(5.dp))
        informations(navController)
    }

}

@Composable
fun mediumAndExpandedScreen(navController: NavController){
    Row(){
        Column(modifier = Modifier.padding(16.dp)){
            Spacer(Modifier.height(15.dp))
            photoProfil()

        }
        Column (horizontalAlignment = Alignment.CenterHorizontally ){

            Spacer(Modifier.height(5.dp))
            informations(navController)
        }
    }
}
