package com.google.maps.android.compose.carpooling

import android.R.drawable.ic_menu_myplaces
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color as ColorCompose
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.AdvancedMarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PinConfig
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.R
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

internal val arc_triumf_location = LatLng(44.467410, 26.078668)
internal val berarie_location = LatLng(44.475872, 26.074105)
internal val aviatiei_location = LatLng(44.465864, 26.086192)
internal val victoriei_location = LatLng(44.452259, 26.086386)
internal val third_location = LatLng(44.450000, 26.079386)
internal val ase_location = LatLng(44.447298, 26.096316)

internal val defaultCameraPosition1 = CameraPosition.fromLatLngZoom(ase_location, 14f)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CarPoolingScreen(
    onRideEnd: () -> Unit,
) {
    // Observing and controlling the camera's state can be done with a CameraPositionState
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition1
    }
    val mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    val aseMarkerState = rememberMarkerState(position = ase_location)
    val berarieMarkerState = rememberMarkerState(position = berarie_location)
    val firstClientMarkerState = rememberMarkerState(position = ase_location)
    val firstClientDstMarkerState = rememberMarkerState(position = victoriei_location)

    val secondClientMarkerState = rememberMarkerState(position = aviatiei_location)
    val secondClientDstMarkerState = rememberMarkerState(position = berarie_location)

    val thirdClientMarkerState = rememberMarkerState(position = third_location)
    val thirdClientDstMarkerState = rememberMarkerState(position = aviatiei_location)

    var showFirstClientRoad by remember { mutableStateOf(false) }
    var showSecondClientRoad by remember { mutableStateOf(false) }
    var showThirdClientRoad by remember { mutableStateOf(false) }

    val sofer1MarkerState = rememberMarkerState(position = victoriei_location)
    val sofer2MarkerState = rememberMarkerState(position = aviatiei_location)

    var visible1 by remember { mutableStateOf(true) }
    var visible2 by remember { mutableStateOf(true) }
    var visible3 by remember { mutableStateOf(true) }

    var showProfilePage by remember { mutableStateOf(false) }

    var showDriverRoad by remember { mutableStateOf(false) }

    var event by remember { mutableIntStateOf(0) }

    LaunchedEffect(event) {
        when (event) {
            1 -> showDriverRoad = true
            4 -> {
                showFirstClientRoad = false
                visible1 = false
            }

            5 -> visible2 = false
            6 -> {
                visible3 = false
                showSecondClientRoad = false
                onRideEnd()
            }
        }
    }


    if (!showProfilePage) {
        IncomingClientsBottomSheet(showRoad0 = {
            showFirstClientRoad = true
        }, showRoad1 = {
            showSecondClientRoad = true
        }, showRoad2 = {
            showThirdClientRoad = true
        }, declineClient2 = {
            showThirdClientRoad = false
        }, event = event) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                googleMapOptionsFactory = {
                    GoogleMapOptions().mapId("DEMO_MAP_ID")
                },
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
                onPOIClick = {
                    Log.d("car pooling", "POI clicked: ${it.name}")
                },
                mapColorScheme = ComposeMapColorScheme.DARK,
            ) {
                if (showDriverRoad) {
                    DrawDriverRoad(visible1, visible2, visible3)
                }
                DrawFirstClientRoad(visible = showFirstClientRoad)
                DrawSecondClientRoad(visible = showSecondClientRoad)
                DrawThirdClientRoad(visible = showThirdClientRoad)



                AdvancedMarker(
                    state = aseMarkerState,
                    visible = event < 4,
                    collisionBehavior = AdvancedMarkerOptions.CollisionBehavior.OPTIONAL_AND_HIDES_LOWER_PRIORITY,
                    pinConfig = textPinConfig("ASE"),
                    zIndex = 1f,
                    title = "Plecarea soferului"
                )

                AdvancedMarker(
                    state = sofer1MarkerState,
                    visible = event == 4,
                    collisionBehavior = AdvancedMarkerOptions.CollisionBehavior.OPTIONAL_AND_HIDES_LOWER_PRIORITY,
                    pinConfig = humanPinConfig(),
                    zIndex = 3f,
                    title = "Sofer1"
                )

                AdvancedMarker(
                    state = sofer2MarkerState,
                    visible = event == 5,
                    collisionBehavior = AdvancedMarkerOptions.CollisionBehavior.REQUIRED,
                    pinConfig = humanPinConfig(),
                    zIndex = 3f,
                    title = "Sofer2"
                )
                AdvancedMarker(
                    state = berarieMarkerState,
                    collisionBehavior = 1,
                    visible = event < 6,
                    pinConfig = textPinConfig("B"),
                    title = "Destinatie",
                )

                AdvancedMarker(
                    state = firstClientMarkerState,
                    collisionBehavior = 1,
                    pinConfig = textPinConfig("C1"),
                    visible = event in 2..3,
                    zIndex = 2f,
                    title = "Client1"
                )
                AdvancedMarker(
                    state = firstClientDstMarkerState,
                    collisionBehavior = 1,
                    pinConfig = textPinConfig("D1"),
                    visible = showFirstClientRoad,
                    zIndex = 2f,
                    title = "Client1"
                )

                AdvancedMarker(
                    state = thirdClientMarkerState,
                    collisionBehavior = 1,
                    pinConfig = textPinConfig("C3"),
                    visible = event == 3,
                    zIndex = 3f,
                    title = "Client3"
                )
                AdvancedMarker(
                    state = thirdClientDstMarkerState,
                    collisionBehavior = 1,
                    pinConfig = textPinConfig("D3"),
                    visible = showThirdClientRoad,
                    zIndex = 4f,
                    title = "Client3dst"
                )

                AdvancedMarker(
                    state = secondClientMarkerState,
                    collisionBehavior = 1,
                    pinConfig = textPinConfig("C2"),
                    visible = event in 3..5,
                    zIndex = 2f,
                    title = "Client2"
                )
                AdvancedMarker(
                    state = secondClientDstMarkerState,
                    collisionBehavior = 1,
                    pinConfig = textPinConfig("D2"),
                    visible = showSecondClientRoad && event < 5,
                    zIndex = 2f,
                    title = "Client2"
                )
            }
        }
    } else {
        ProfilePage()
    }

    var showProfileButton by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = androidx.compose.ui.graphics.Color.Transparent)
    ) {
        if (event >= 6 && showProfileButton) {
            IconButton(
                onClick = {
                    showProfilePage = true
                    showProfileButton = false
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 100.dp)
                    .background(color = androidx.compose.ui.graphics.Color.White)
            ) {
                Image(painterResource(R.drawable.user_profile_icon), contentDescription = null)
            }
        }
        Box(modifier = Modifier
            .size(100.dp)
            .clickable {
                event += 1
            }
            .background(androidx.compose.ui.graphics.Color.Transparent)
            .align(Alignment.TopEnd)) {}
    }


}

@Composable
fun ProfilePage() {
    val context = LocalContext.current
    var showLeaderboard by remember { mutableStateOf(false) }
    if (showLeaderboard) {
        LeaderboardPage(showProfile = {
            showLeaderboard = false
        })
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = androidx.compose.ui.graphics.Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(modifier = Modifier.padding(top = 60.dp)) {
                Text(
                    "Profil",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 26.sp,
                    modifier = Modifier.align(Alignment.Center),
                )
                Button(onClick = {
                    showLeaderboard = true
                }, modifier = Modifier.align(Alignment.CenterEnd)) {
                    Text("Leaderboard")
                }
            }


            Text("Nume: Sofer GoTogether", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            Image(
                painterResource(R.drawable.user_icon_profile_room),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = "Rating: 4.9",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = androidx.compose.ui.graphics.Color.Blue
                )
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "Star",
                    tint = ColorCompose(0xFFFFD700),
                    modifier = Modifier.size(16.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = "Nivel de economisire: 2/10",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = androidx.compose.ui.graphics.Color.Blue
                )
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "Star",
                    tint = ColorCompose(0xFFFFD700),
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                "Medalii obtinute",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 30.dp)
            )
            Image(
                painterResource(R.drawable.badges),
                contentDescription = null,
                modifier = Modifier.size(width = 300.dp, height = 100.dp)
            )

            Text(text = "Istoric comenzi", fontSize = 20.sp, fontWeight = FontWeight.Bold)


            Card() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        clients.forEach {
                            if (it != clients[2]) ClientEntry2(it)
                        }
                    }
                    Button(onClick = {
                        Toast.makeText(context, "Clientii au fost notificati!", Toast.LENGTH_LONG)
                            .show()
                    }) {
                        Text(
                            "Repeta comanda in fiecare sambata la ora 23:00",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun LeaderboardPage(showProfile: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = androidx.compose.ui.graphics.Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            "Leaderboard",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp,
            modifier = Modifier.padding(top = 60.dp),
        )
        Card(modifier = Modifier.fillMaxWidth(0.8f)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                ClientEntry2(
                    client = clients[0].copy(
                        name = "1. Tudor (Top Score) - 560 comenzi",
                        starRating = 5.0f
                    )
                )
                ClientEntry2(client = clients[0].copy(name = "2. Alex - 200 comenzi"))
                ClientEntry2(client = clients[1].copy(name = "3. Andrei - 100 comenzi", starRating = 4.7f))
                ClientEntry2(client = clients[1].copy(name = "4. Ioan - 5 comenzi", starRating = 4.3f))
                ClientEntry2(client = clients[1].copy(name = "5. Matei - 2 comenzi", starRating = 4.2f))
            }
        }
        Button(onClick = {
            showProfile()
        }, modifier = Modifier.padding(top = 30.dp)) {
            Text("Profil")
        }

    }
}

@Composable
fun ClientEntry2(client: ClientEntry) {
    Column(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material.Text(
            text = client.name, fontSize = 18.sp, color = androidx.compose.ui.graphics.Color.Black
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            modifier = Modifier.padding(top = 4.dp)
        ) {
            androidx.compose.material.Text(text = client.starRating.toString())
            Icon(
                Icons.Filled.Star,
                contentDescription = "Star",
                tint = ColorCompose(0xFFFFD700),
                modifier = Modifier.size(16.dp)
            )
        }

        Image(
            painter = painterResource(id = client.iconResId), // Replace with your image resource
            contentDescription = "User Icon",
            modifier = Modifier
                .size(48.dp)
                .background(androidx.compose.ui.graphics.Color.Gray, shape = CircleShape)
                .padding(4.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

    }
}

@Preview
@Composable
fun ProfilePreview() {
    LeaderboardPage() {

    }
}

private fun humanPinConfig(): PinConfig {
    val glyphImage: Int = ic_menu_myplaces
    val descriptor = BitmapDescriptorFactory.fromResource(glyphImage)
    val pinConfig = PinConfig.builder().setGlyph(PinConfig.Glyph(descriptor)).build()
    return pinConfig
}

private fun textView(context: Context, text: String): TextView {
    val textView = TextView(context)
    textView.text = text
    textView.setBackgroundColor(Color.BLACK)
    textView.setTextColor(Color.YELLOW)

    return textView
}

private fun textPinConfig(text: String, color: Int = Color.BLACK): PinConfig {
    val glyphOne = PinConfig.Glyph(text, color)
    val pinConfig = PinConfig.builder().setGlyph(glyphOne).build()
    return pinConfig
}