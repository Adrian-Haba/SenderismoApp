package com.example.senderismoapp
import android.os.Bundle
import android.content.Intent
import android.net.Uri
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
import androidx.navigation.NavController
import com.example.senderismoapp.ui.theme.SenderismoAppTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.IconButton
//import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.Polyline
import com.google.android.gms.maps.model.CameraPosition
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import androidx.compose.runtime.LaunchedEffect
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import android.location.Location
import android.os.Looper
import androidx.compose.runtime.DisposableEffect
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import androidx.compose.foundation.clickable
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import com.google.maps.android.compose.MarkerComposable
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.common.api.ResolvableApiException
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.maps.model.LatLngBounds


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SenderismoAppTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "inicio"
                ) {

                    composable("inicio") {
                        SenderismoHomeScreen(
                            navController = navController
                        )
                    }

                    composable("mapa") {
                        MapScreen(
                            navController = navController
                        )
                    }

                    composable("rutas") {
                        Text("Pantalla de rutas")
                    }

                    composable("detalle_ruta") {
                        RouteDetailScreen(
                            navController = navController
                        )
                    }
                    composable("navegacion") {
                        NavigationScreen(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SenderismoHomeScreen(
    navController: NavHostController
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                pantallaActual = "inicio"
            )
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

                RouteCard(
                    navController = navController
                )
            }
        }
    }
}
@Composable
fun MapScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                pantallaActual = "mapa"
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Text(
                text = "Mapa",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF123B3B)
            )
        }
    }
}
@Composable
fun RouteDetailScreen(
    navController: NavHostController
) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
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

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(
                            start = 12.dp,
                            top = 12.dp
                        )
                ) {
                    Text(
                        text = "‹",
                        fontSize = 42.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    )
                }

                Text(
                    text = "🌳   🌿   🌊   🌳",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 40.sp
                )

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(18.dp),
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
            }

            // ---------------------------------------------------------
            // INFORMACIÓN DE LA RUTA
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
                    text = "Ruta del Parque del Alamillo",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF123B3B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "📍 Parque del Alamillo · Sevilla",
                    fontSize = 16.sp,
                    color = Color(0xFF65777A)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // -----------------------------------------------------
                // ESTADÍSTICAS
                // -----------------------------------------------------

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
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
                            value = "+47 m",
                            label = "Desnivel"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // -----------------------------------------------------
                // DESCRIPCIÓN
                // -----------------------------------------------------

                Text(
                    text = "Sobre esta ruta",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF123B3B)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Recorrido circular por el Parque del Alamillo. "
                            + "Una ruta sencilla para disfrutar del entorno natural, "
                            + "observar la vegetación y descubrir la fauna de la zona.",
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF65777A)
                )

                Spacer(modifier = Modifier.height(30.dp))

                // -----------------------------------------------------
                // BOTÓN COMENZAR
                // -----------------------------------------------------

                Button(
                    onClick = {
                        navController.navigate("navegacion")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF238B62),
                        contentColor = Color.White
                    )
                ) {

                    Text(
                        text = "Comenzar ruta  →",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

data class RoutePoint(
    val latitude: Double,
    val longitude: Double
)

@Composable
fun NavigationScreen(
    navController: NavHostController
) {
    var navegando by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val permisoUbicacion = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->
        val ubicacionPermitida =
            permisos[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permisos[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (ubicacionPermitida) {
            // Más adelante activaremos aquí el GPS
        }
    }

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var ubicacionActual by remember { mutableStateOf<LatLng?>(null) }
    var ubicacionActivada by remember { mutableStateOf(true) }

    val locationRequest = remember {
        LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            2000L
        ).build()
    }
    val settingsClient = remember {
        LocationServices.getSettingsClient(context)
    }

    val settingsRequest = remember {
        LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->

            if (event == Lifecycle.Event.ON_RESUME) {

                settingsClient
                    .checkLocationSettings(settingsRequest)
                    .addOnSuccessListener {
                        ubicacionActivada = true
                    }
                    .addOnFailureListener {
                        ubicacionActivada = false
                    }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    val locationCallback = remember {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation ?: return

                ubicacionActual = LatLng(
                    location.latitude,
                    location.longitude
                )
            }
        }
    }

    DisposableEffect(Unit) {

        ubicacionActivada = LocationServices
            .getSettingsClient(context)
            .checkLocationSettings(
                LocationRequest.Builder(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    2000L
                ).build().let {
                    com.google.android.gms.location.LocationSettingsRequest.Builder()
                        .addLocationRequest(it)
                        .build()
                }
            )
            .isSuccessful

        val tienePermiso =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

        if (tienePermiso) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }

        onDispose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    var distanciaAlInicio by remember { mutableStateOf<Float?>(null) }

    val estaEnElInicio =
        distanciaAlInicio != null && distanciaAlInicio!! <= 10f

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFE5EEE8))
        ) {

            // ---------------------------------------------------------
            // MAPA
            // ---------------------------------------------------------

            // val alamillo = LatLng(37.4158790, -5.9929070) > PRUEBA

            val rutaPrueba = listOf(
                RoutePoint(37.4158790, -5.9929070),
                RoutePoint(37.4159460, -5.9928210),
                RoutePoint(37.4160220, -5.9927570),
                RoutePoint(37.4160920, -5.9926750),
                RoutePoint(37.4161710, -5.9926060),
                RoutePoint(37.4162450, -5.9925300),
                RoutePoint(37.4163010, -5.9926270),
                RoutePoint(37.4163920, -5.9926810),
                RoutePoint(37.4164280, -5.9927850),
                RoutePoint(37.4164220, -5.9929110),
                RoutePoint(37.4164740, -5.9930160),
                RoutePoint(37.4164350, -5.9931280),
                RoutePoint(37.4163600, -5.9932010),
                RoutePoint(37.4163240, -5.9933080),
                RoutePoint(37.4164120, -5.9932590),
                RoutePoint(37.4163170, -5.9932610),
                RoutePoint(37.4162410, -5.9933560),
                RoutePoint(37.4163300, -5.9932930),
                RoutePoint(37.4164080, -5.9933600),
                RoutePoint(37.4164340, -5.9932430),
                RoutePoint(37.4165350, -5.9932170),
                RoutePoint(37.4166250, -5.9932280),
                RoutePoint(37.4167260, -5.9932590),
                RoutePoint(37.4168280, -5.9932820),
                RoutePoint(37.4169140, -5.9933220),
                RoutePoint(37.4170100, -5.9933720),
                RoutePoint(37.4171010, -5.9933880),
                RoutePoint(37.4171920, -5.9934530),
                RoutePoint(37.4172900, -5.9934760),
                RoutePoint(37.4173780, -5.9935030),
                RoutePoint(37.4174830, -5.9935200),
                RoutePoint(37.4175820, -5.9935380),
                RoutePoint(37.4176690, -5.9935800),
                RoutePoint(37.4177580, -5.9935380),
                RoutePoint(37.4178410, -5.9934880),
                RoutePoint(37.4177420, -5.9935490),
                RoutePoint(37.4176830, -5.9936530),
                RoutePoint(37.4176060, -5.9937470),
                RoutePoint(37.4175880, -5.9938840),
                RoutePoint(37.4175650, -5.9939980),
                RoutePoint(37.4175340, -5.9941220),
                RoutePoint(37.4174900, -5.9942310),
                RoutePoint(37.4174490, -5.9943420),
                RoutePoint(37.4174570, -5.9944550),
                RoutePoint(37.4174150, -5.9945620),
                RoutePoint(37.4173210, -5.9945160),
                RoutePoint(37.4172330, -5.9944620),
                RoutePoint(37.4173230, -5.9945290),
                RoutePoint(37.4174110, -5.9945680),
                RoutePoint(37.4175020, -5.9946280),
                RoutePoint(37.4175960, -5.9945670),
                RoutePoint(37.4176730, -5.9945040),
                RoutePoint(37.4177550, -5.9944470),
                RoutePoint(37.4178340, -5.9943750),
                RoutePoint(37.4179070, -5.9943010),
                RoutePoint(37.4179970, -5.9942740),
                RoutePoint(37.4180830, -5.9942000),
                RoutePoint(37.4181740, -5.9941450),
                RoutePoint(37.4182420, -5.9940560),
                RoutePoint(37.4183240, -5.9939820),
                RoutePoint(37.4184050, -5.9939210),
                RoutePoint(37.4185040, -5.9939030),
                RoutePoint(37.4186110, -5.9938780),
                RoutePoint(37.4187110, -5.9938890),
                RoutePoint(37.4188080, -5.9938670),
                RoutePoint(37.4189060, -5.9938480),
                RoutePoint(37.4189990, -5.9938310),
                RoutePoint(37.4190940, -5.9938520),
                RoutePoint(37.4191860, -5.9938560),
                RoutePoint(37.4192820, -5.9938760),
                RoutePoint(37.4193630, -5.9939380),
                RoutePoint(37.4194150, -5.9940300),
                RoutePoint(37.4193720, -5.9941350),
                RoutePoint(37.4193660, -5.9942500),
                RoutePoint(37.4193900, -5.9943690),
                RoutePoint(37.4193780, -5.9944810),
                RoutePoint(37.4193600, -5.9946120),
                RoutePoint(37.4193320, -5.9947210),
                RoutePoint(37.4192420, -5.9946890),
                RoutePoint(37.4191860, -5.9945980),
                RoutePoint(37.4190910, -5.9945790),
                RoutePoint(37.4190170, -5.9946600),
                RoutePoint(37.4190200, -5.9947790),
                RoutePoint(37.4190380, -5.9948920),
                RoutePoint(37.4190480, -5.9950110),
                RoutePoint(37.4190490, -5.9951300),
                RoutePoint(37.4189670, -5.9951930),
                RoutePoint(37.4188780, -5.9952280),
                RoutePoint(37.4187840, -5.9952350),
                RoutePoint(37.4187180, -5.9953360),
                RoutePoint(37.4186510, -5.9954180),
                RoutePoint(37.4187380, -5.9954760),
                RoutePoint(37.4187110, -5.9956020),
                RoutePoint(37.4186550, -5.9957170),
                RoutePoint(37.4186360, -5.9958270),
                RoutePoint(37.4186280, -5.9959470),
                RoutePoint(37.4186540, -5.9960590),
                RoutePoint(37.4187770, -5.9960730),
                RoutePoint(37.4188700, -5.9959900),
                RoutePoint(37.4189300, -5.9958920),
                RoutePoint(37.4189260, -5.9957690),
                RoutePoint(37.4189450, -5.9956590),
                RoutePoint(37.4189570, -5.9955390),
                RoutePoint(37.4189910, -5.9954310),
                RoutePoint(37.4190500, -5.9955190),
                RoutePoint(37.4191590, -5.9955580),
                RoutePoint(37.4192490, -5.9955880),
                RoutePoint(37.4191580, -5.9955650),
                RoutePoint(37.4190610, -5.9955720),
                RoutePoint(37.4189710, -5.9955180),
                RoutePoint(37.4188890, -5.9954680),
                RoutePoint(37.4189750, -5.9955430),
                RoutePoint(37.4190630, -5.9955690),
                RoutePoint(37.4191360, -5.9956490),
                RoutePoint(37.4192290, -5.9956900),
                RoutePoint(37.4193160, -5.9957230),
                RoutePoint(37.4193920, -5.9956570),
                RoutePoint(37.4194950, -5.9956410),
                RoutePoint(37.4195970, -5.9956260),
                RoutePoint(37.4196980, -5.9956620),
                RoutePoint(37.4197870, -5.9957060),
                RoutePoint(37.4197920, -5.9958310),
                RoutePoint(37.4197250, -5.9959210),
                RoutePoint(37.4196560, -5.9960110),
                RoutePoint(37.4196080, -5.9961100),
                RoutePoint(37.4195330, -5.9961980),
                RoutePoint(37.4194600, -5.9963020),
                RoutePoint(37.4194030, -5.9963920),
                RoutePoint(37.4193340, -5.9964830),
                RoutePoint(37.4192430, -5.9965040),
                RoutePoint(37.4191620, -5.9965670),
                RoutePoint(37.4192550, -5.9965660),
                RoutePoint(37.4193160, -5.9966650),
                RoutePoint(37.4193270, -5.9967820),
                RoutePoint(37.4193310, -5.9969050),
                RoutePoint(37.4193390, -5.9970180),
                RoutePoint(37.4193830, -5.9971290),
                RoutePoint(37.4194020, -5.9972410),
                RoutePoint(37.4194340, -5.9973500),
                RoutePoint(37.4194600, -5.9974690),
                RoutePoint(37.4194830, -5.9975780),
                RoutePoint(37.4194830, -5.9976950),
                RoutePoint(37.4194650, -5.9978130),
                RoutePoint(37.4194500, -5.9976900),
                RoutePoint(37.4195450, -5.9976570),
                RoutePoint(37.4196360, -5.9976700),
                RoutePoint(37.4197090, -5.9977410),
                RoutePoint(37.4197920, -5.9977860),
                RoutePoint(37.4198660, -5.9978680),
                RoutePoint(37.4199300, -5.9979490),
                RoutePoint(37.4199560, -5.9980630),
                RoutePoint(37.4199890, -5.9981870),
                RoutePoint(37.4200110, -5.9983080),
                RoutePoint(37.4199980, -5.9984230),
                RoutePoint(37.4200860, -5.9984710),
                RoutePoint(37.4201890, -5.9984690),
                RoutePoint(37.4202130, -5.9985820),
                RoutePoint(37.4201700, -5.9986870),
                RoutePoint(37.4202330, -5.9985990),
                RoutePoint(37.4202180, -5.9984800),
                RoutePoint(37.4201530, -5.9983910),
                RoutePoint(37.4200670, -5.9984550),
                RoutePoint(37.4199700, -5.9984690),
                RoutePoint(37.4198700, -5.9984960),
                RoutePoint(37.4197660, -5.9985340),
                RoutePoint(37.4196700, -5.9985550),
                RoutePoint(37.4195640, -5.9985840),
                RoutePoint(37.4194690, -5.9986170),
                RoutePoint(37.4193660, -5.9986490),
                RoutePoint(37.4192590, -5.9986880),
                RoutePoint(37.4191660, -5.9987160),
                RoutePoint(37.4190710, -5.9987510),
                RoutePoint(37.4189820, -5.9987740),
                RoutePoint(37.4188780, -5.9988030),
                RoutePoint(37.4187800, -5.9988260),
                RoutePoint(37.4186800, -5.9988460),
                RoutePoint(37.4185790, -5.9988960),
                RoutePoint(37.4184830, -5.9989300),
                RoutePoint(37.4184830, -5.9990540),
                RoutePoint(37.4184830, -5.9991730),
                RoutePoint(37.4184770, -5.9992920),
                RoutePoint(37.4184560, -5.9994030),
                RoutePoint(37.4184150, -5.9992910),
                RoutePoint(37.4184100, -5.9991750),
                RoutePoint(37.4183830, -5.9990510),
                RoutePoint(37.4183220, -5.9989460),
                RoutePoint(37.4182290, -5.9989130),
                RoutePoint(37.4181460, -5.9988610),
                RoutePoint(37.4180880, -5.9987650),
                RoutePoint(37.4181000, -5.9988820),
                RoutePoint(37.4180020, -5.9988850),
                RoutePoint(37.4179130, -5.9989050),
                RoutePoint(37.4178210, -5.9989360),
                RoutePoint(37.4177230, -5.9989250),
                RoutePoint(37.4176240, -5.9989400),
                RoutePoint(37.4175240, -5.9989790),
                RoutePoint(37.4174260, -5.9989800),
                RoutePoint(37.4173270, -5.9989770),
                RoutePoint(37.4172430, -5.9990290),
                RoutePoint(37.4171410, -5.9990000),
                RoutePoint(37.4170850, -5.9988890),
                RoutePoint(37.4169860, -5.9989020),
                RoutePoint(37.4168930, -5.9989000),
                RoutePoint(37.4167970, -5.9988850),
                RoutePoint(37.4167110, -5.9988490),
                RoutePoint(37.4166310, -5.9987790),
                RoutePoint(37.4165280, -5.9987440),
                RoutePoint(37.4164410, -5.9987090),
                RoutePoint(37.4163450, -5.9987250),
                RoutePoint(37.4162530, -5.9987260),
                RoutePoint(37.4161420, -5.9987570),
                RoutePoint(37.4162390, -5.9987710),
                RoutePoint(37.4162290, -5.9988830),
                RoutePoint(37.4161660, -5.9989990),
                RoutePoint(37.4160750, -5.9989480),
                RoutePoint(37.4160000, -5.9988790),
                RoutePoint(37.4158980, -5.9988890),
                RoutePoint(37.4158080, -5.9989050),
                RoutePoint(37.4158770, -5.9988310),
                RoutePoint(37.4159510, -5.9987550),
                RoutePoint(37.4160540, -5.9987160),
                RoutePoint(37.4161520, -5.9987270),
                RoutePoint(37.4162460, -5.9987100),
                RoutePoint(37.4163080, -5.9986120),
                RoutePoint(37.4163320, -5.9984990),
                RoutePoint(37.4163770, -5.9983930),
                RoutePoint(37.4164240, -5.9982910),
                RoutePoint(37.4164330, -5.9981570),
                RoutePoint(37.4164100, -5.9980460),
                RoutePoint(37.4164340, -5.9979220),
                RoutePoint(37.4164720, -5.9978170),
                RoutePoint(37.4164610, -5.9977030),
                RoutePoint(37.4164070, -5.9975880),
                RoutePoint(37.4163780, -5.9974600),
                RoutePoint(37.4163230, -5.9973600),
                RoutePoint(37.4162920, -5.9972430),
                RoutePoint(37.4162220, -5.9971660),
                RoutePoint(37.4161400, -5.9970820),
                RoutePoint(37.4160620, -5.9969990),
                RoutePoint(37.4160240, -5.9968850),
                RoutePoint(37.4159920, -5.9967780),
                RoutePoint(37.4159080, -5.9967010),
                RoutePoint(37.4158520, -5.9965770),
                RoutePoint(37.4157900, -5.9964800),
                RoutePoint(37.4157220, -5.9963880),
                RoutePoint(37.4156550, -5.9963110),
                RoutePoint(37.4155630, -5.9963420),
                RoutePoint(37.4154740, -5.9963240),
                RoutePoint(37.4153870, -5.9962560),
                RoutePoint(37.4153000, -5.9962950),
                RoutePoint(37.4151960, -5.9963010),
                RoutePoint(37.4151040, -5.9963250),
                RoutePoint(37.4150830, -5.9962100),
                RoutePoint(37.4150480, -5.9960920),
                RoutePoint(37.4150030, -5.9959890),
                RoutePoint(37.4149980, -5.9958720),
                RoutePoint(37.4150030, -5.9957570),
                RoutePoint(37.4149990, -5.9956400),
                RoutePoint(37.4149610, -5.9955370),
                RoutePoint(37.4149290, -5.9954200),
                RoutePoint(37.4149320, -5.9953030),
                RoutePoint(37.4149160, -5.9951810),
                RoutePoint(37.4148820, -5.9950680),
                RoutePoint(37.4148470, -5.9949570),
                RoutePoint(37.4148250, -5.9948330),
                RoutePoint(37.4148190, -5.9947000)
            )
            LaunchedEffect(ubicacionActual) {
                ubicacionActual?.let { ubicacion ->

                    val resultados = FloatArray(1)

                    Location.distanceBetween(
                        ubicacion.latitude,
                        ubicacion.longitude,
                        rutaPrueba.first().latitude,
                        rutaPrueba.first().longitude,
                        resultados
                    )

                    distanciaAlInicio = resultados[0]
                }
            }
            val puntosMapa = rutaPrueba.map {
                LatLng(it.latitude, it.longitude)
            }

            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(
                    LatLng(37.4175, -5.9960),
                    15.5f
                )
            }
            val inicioMarkerState = remember {
                MarkerState(
                    position = LatLng(
                        rutaPrueba.first().latitude,
                        rutaPrueba.first().longitude
                    )
                )
            }

            val finMarkerState = remember {
                MarkerState(
                    position = LatLng(
                        rutaPrueba.last().latitude,
                        rutaPrueba.last().longitude
                    )
                )
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                // BOLA VERDE - INICIO
                MarkerComposable(
                    state = inicioMarkerState,
                    keys = arrayOf("inicio")
                ) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF238B62))
                            .border(
                                width = 3.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                    )
                }

                // BOLA ROJA - FIN
                MarkerComposable(
                    state = finMarkerState,
                    keys = arrayOf("fin")
                ) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE53935))
                            .border(
                                width = 3.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                    )
                }
                // RUTA
                Polyline(
                    points = puntosMapa,
                    color = Color(0xFF2E7D32),
                    width = 12f
                )

                // BOLA AZUL - MI UBICACIÓN
                ubicacionActual?.let { posicion ->

                    MarkerComposable(
                        state = MarkerState(
                            position = posicion
                        ),
                        keys = arrayOf(
                            posicion.latitude,
                            posicion.longitude
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF1976D2))
                                .border(
                                    width = 3.dp,
                                    color = Color.White,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
            // ---------------------------------------------------------
            // BOTÓN VOLVER
            // ---------------------------------------------------------

            Surface(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart),
                shape = RoundedCornerShape(50),
                color = Color.White,
                shadowElevation = 5.dp
            ) {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {

                    Text(
                        text = "‹",
                        fontSize = 38.sp,
                        color = Color(0xFF123B3B)
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 70.dp,
                        end = 16.dp,
                        top = 16.dp
                    )
                    .align(Alignment.TopCenter),
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                shadowElevation = 5.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 18.dp,
                            vertical = 12.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Distancia al inicio",
                        fontSize = 13.sp,
                        color = Color(0xFF65777A)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = when {
                            !ubicacionActivada -> "⚠️ Activa la ubicación"
                            distanciaAlInicio != null -> if (distanciaAlInicio!! < 1000f) {
                                "${distanciaAlInicio!!.toInt()} m"
                            } else {
                                val kilometros = distanciaAlInicio!! / 1000f
                                "${String.format("%.2f", kilometros).replace('.', ',')} km"
                            }
                            else -> "Buscando ubicación…"
                        },
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF123B3B)
                    )
                }
            }
            // ---------------------------------------------------------
            // PANEL INFERIOR
            // ---------------------------------------------------------

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(
                    topStart = 28.dp,
                    topEnd = 28.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {

                    Text(
                        text = "Ruta del Parque del Alamillo",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF123B3B)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // -------------------------------------------------
                    // ESTADÍSTICAS
                    // -------------------------------------------------

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        NavigationInfo(
                            value = "2,88 km",
                            label = "Ruta"
                        )

                        NavigationInfo(
                            value = "0,00 km",
                            label = "Recorrido"
                        )

                        NavigationInfo(
                            value = "53 min",
                            label = "Estimado"
                        )
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    // -------------------------------------------------
                    // ESTADO
                    // -------------------------------------------------

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFE8F4EF)
                    ) {

                        Text(
                            text = if (navegando) {
                                "🟢 Navegación en curso"
                            } else {
                                "📍 Preparado para comenzar"
                            },
                            modifier = Modifier.padding(14.dp),
                            color = Color(0xFF18794E),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            val tienePermiso =
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) == PackageManager.PERMISSION_GRANTED ||
                                        ContextCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.ACCESS_COARSE_LOCATION
                                        ) == PackageManager.PERMISSION_GRANTED

                            if (tienePermiso) {
                                if (!ubicacionActivada) {
                                    return@Button
                                }
                                if (estaEnElInicio) {
                                    navegando = !navegando
                                } else {
                                    val inicioRuta = rutaPrueba.first()

                                    val uri = Uri.parse(
                                        "google.navigation:q=${inicioRuta.latitude},${inicioRuta.longitude}"
                                    )

                                    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                                        setPackage("com.google.android.apps.maps")
                                    }

                                    context.startActivity(intent)
                                }
                            } else {
                                permisoUbicacion.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF238B62),
                            contentColor = Color.White
                        )
                    ) {

                        Text(
                            text = if (navegando) {
                                "Finalizar navegación"
                            } else {
                                "Iniciar navegación"
                            },
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun NavigationInfo(
    value: String,
    label: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF123B3B)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF718083)
        )
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
fun RouteCard(
    navController: NavHostController
) {

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
                        navController.navigate("detalle_ruta")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF238B62),
                        contentColor = Color.White
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
fun BottomNavigationBar(
    navController: NavHostController,
    pantallaActual: String
) {

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
                selected = pantallaActual == "inicio",
                onClick = {
                    navController.navigate("inicio")
                }
            )

            BottomNavigationItem(
                icon = "▱",
                label = "Mapa",
                selected = pantallaActual == "mapa",
                onClick = {
                    navController.navigate("mapa")
                }
            )

            BottomNavigationItem(
                icon = "☷",
                label = "Rutas",
                selected = pantallaActual == "rutas",
                onClick = {
                    navController.navigate("rutas")
                }
            )

            BottomNavigationItem(
                icon = "♙",
                label = "Perfil",
                selected = false,
                onClick = {
                    // Lo implementaremos más adelante
                }
            )
        }
    }
}

@Composable
fun BottomNavigationItem(
    icon: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier.clickable(onClick = onClick),
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
        val navController = rememberNavController()

        SenderismoHomeScreen(
            navController = navController
        )
    }
}
