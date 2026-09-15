package com.example.senderismoapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.senderismoapp.ui.theme.SenderismoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SenderismoAppTheme {
                SenderismoHomeScreen()
            }
        }
    }
}

@Composable
fun SenderismoHomeScreen() {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar()
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF7FAF8))
        ) {

            // ---------------------------------------------------------
            // CABECERA
            // ---------------------------------------------------------

            HeroSection()

            // ---------------------------------------------------------
            // CONTENIDO
            // ---------------------------------------------------------

            Column(
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 24.dp,
                    bottom = 30.dp
                )
            ) {

                Text(
                    text = "📍  Selecciona una zona",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF123B3B)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Elige la zona que quieres explorar y descubre sus rutas.",
                    fontSize = 16.sp,
                    color = Color(0xFF66777A)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // -----------------------------------------------------
                // ZONA
                // -----------------------------------------------------

                ZoneCard()

                Spacer(modifier = Modifier.height(30.dp))

                // -----------------------------------------------------
                // RUTAS DESTACADAS
                // -----------------------------------------------------

                Text(
                    text = "★  Rutas destacadas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF123B3B)
                )

                Spacer(modifier = Modifier.height(16.dp))

                RouteCard()
            }
        }
    }
}

@Composable
fun HeroSection() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF397A78),
                        Color(0xFF74A99A),
                        Color(0xFFB8D5C8)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 45.dp,
                    bottom = 25.dp
                ),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "🏔️  SenderismoApp",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Descubre. Explora. Conecta.",
                fontSize = 19.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Rutas, naturaleza y nuevas aventuras\nte esperan.",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}

@Composable
fun ZoneCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8F4EF)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Imagen provisional.
            // Más adelante sustituiremos esto por la fotografía
            // real de la zona almacenada en nuestra API.
            Box(
                modifier = Modifier
                    .size(width = 115.dp, height = 90.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF65A978),
                                Color(0xFFB7D8A8)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🌳\n🌊",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Parque del Alamillo",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF123B3B)
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Sevilla",
                    fontSize = 16.sp,
                    color = Color(0xFF65777A)
                )
            }

            Text(
                text = "›",
                fontSize = 40.sp,
                color = Color(0xFF18794E),
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
fun RouteCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column {

            // ---------------------------------------------------------
            // IMAGEN PROVISIONAL DE LA RUTA
            // ---------------------------------------------------------

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF77A978),
                                Color(0xFFBCD6B2),
                                Color(0xFF87B9B0)
                            )
                        )
                    )
            ) {

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(14.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFF238B62)
                ) {

                    Text(
                        text = "Fácil",
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                Text(
                    text = "🌳   🌿   🌊   🌳",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 34.sp
                )
            }

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Ruta del Parque del Alamillo",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF123B3B)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // -----------------------------------------------------
                // INFORMACIÓN
                // -----------------------------------------------------

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    RouteInfo(
                        value = "2,88 km",
                        label = "Distancia"
                    )

                    RouteInfo(
                        value = "53 min",
                        label = "Tiempo"
                    )

                    RouteInfo(
                        value = "Circular",
                        label = "Tipo"
                    )

                    RouteInfo(
                        value = "+47 / -47 m",
                        label = "Desnivel"
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = {
                        // Próximamente:
                        // abrir detalle de la ruta.
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF238B62)
                    )
                ) {

                    Text(
                        text = "Ver ruta   ›",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun RouteInfo(
    value: String,
    label: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF123B3B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 11.sp,
            color = Color(0xFF718083),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BottomNavigationBar() {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 10.dp,
                    bottom = 10.dp
                ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            BottomNavigationItem(
                icon = "⌂",
                label = "Inicio",
                selected = true
            )

            BottomNavigationItem(
                icon = "▱",
                label = "Mapa",
                selected = false
            )

            BottomNavigationItem(
                icon = "☷",
                label = "Rutas",
                selected = false
            )

            BottomNavigationItem(
                icon = "♙",
                label = "Perfil",
                selected = false
            )
        }
    }
}

@Composable
fun BottomNavigationItem(
    icon: String,
    label: String,
    selected: Boolean
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = icon,
            fontSize = 25.sp,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            color = if (selected) {
                Color(0xFF238B62)
            } else {
                Color(0xFF7B898D)
            }
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            color = if (selected) {
                Color(0xFF238B62)
            } else {
                Color(0xFF7B898D)
            }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SenderismoAppPreview() {
    SenderismoAppTheme {
        SenderismoHomeScreen()
    }
}
