package com.google.maps.android.compose.carpooling

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.R
import kotlinx.coroutines.launch

@Composable
fun DrawFirstClientRoad(visible: Boolean) {
    DrawPoliline(ase_location, victoriei_location, color = 0x80E6E6FA, visible = visible, 2f)
}

@Composable
fun DrawSecondClientRoad(visible: Boolean) {
    DrawPoliline(aviatiei_location, arc_triumf_location, color = 0x80FFDAB9, visible = visible, 2f)
    DrawPoliline(arc_triumf_location, berarie_location, color = 0x80FFDAB9, visible = visible, 2f)
}
@Composable
fun DrawThirdClientRoad(visible: Boolean) {
    DrawPoliline(victoriei_location, aviatiei_location, color = 0x80F08080, visible = visible, 2f)
}

@Composable
fun DrawDriverRoad(visible1: Boolean, visible2: Boolean, visible3: Boolean) {
    DrawPoliline(ase_location, victoriei_location, visible = visible1)
    DrawPoliline(victoriei_location, aviatiei_location, visible = visible2)
    DrawPoliline(aviatiei_location, arc_triumf_location, visible = visible3)
    DrawPoliline(arc_triumf_location, berarie_location, visible = visible3)
}

@Composable
fun DrawPoliline(
    startLocation: LatLng, endLocation: LatLng, color: Long = 0xFF00BFFF, visible: Boolean = true, zIndex: Float = 1f
) {
    Polyline(
        points = listOf(startLocation, endLocation),
        color = Color(color).copy(alpha = 200f),
        width = 14f,
        visible = visible,
        geodesic = true,
        zIndex = zIndex,
        startCap = RoundCap(),                // Smooth rounded ends for the line
        endCap = RoundCap()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun IncomingClientsBottomSheet(
    showRoad0: () -> Unit,
    showRoad1: () -> Unit,
    showRoad2: () -> Unit,
    declineClient2: () -> Unit,
    event: Int,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    var showClient0 by remember { mutableStateOf(true)}
    var showClient1 by remember { mutableStateOf(true)}
    var showClient2 by remember { mutableStateOf(true)}

    Scaffold(
        floatingActionButton = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                ExtendedFloatingActionButton(text = { Text(if (event >= 1) "Clienti" else "Incepe") },
                    containerColor = Color.LightGray,
                    icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                    onClick = { showBottomSheet = true })
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        content()
        if (showBottomSheet) {
            ModalBottomSheet(sheetState = sheetState, shape = MaterialTheme.shapes.large.copy(
                topStart = CornerSize(16.dp),
                topEnd = CornerSize(16.dp),
            ), containerColor = Color.White, onDismissRequest = { showBottomSheet = false }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color(0xFFF9FAFB)) // Light gray background
                ) {
                    if (showClient0 && event >= 2) {
                        ClientItem(clientEntry = clients[0], onShowRoad = {
                            showBottomSheet = false
                            showRoad0()
                        }, onAccept = {
                            showBottomSheet = false
                            showClient0 = false
                            showRoad0()
                        })
                    }

                    if (showClient1 && event >= 3) {
                        ClientItem(clientEntry = clients[1], onShowRoad = {
                            showBottomSheet = false
                            showRoad1()
                        }, onAccept = {
                            showBottomSheet = false
                            showClient1 = false
                            showRoad1()
                        })
                    }
                    if (showClient2 && event >= 3) {
                        ClientItem(clientEntry = clients[2], onShowRoad = {
                            showBottomSheet = false
                            showRoad2()
                        }, onAccept = {
                        }, onDecline = {
                            showBottomSheet = false
                            showClient2 = false
                            declineClient2()
                        }, dangerSign = true)
                    }


                    Spacer(modifier = Modifier.height(16.dp))

                    // Close Button
                    Button(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4285F4))
                    ) {
                        Text(
                            text = "Ascunde", color = Color.White, fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ClientItem(
    clientEntry: ClientEntry, onShowRoad: () -> Unit, onAccept: () -> Unit, onDecline: () -> Unit = {}, dangerSign: Boolean = false,
) {
    Card(
        shape = RoundedCornerShape(8.dp), modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onShowRoad()
            }, elevation = CardDefaults.cardElevation(4.dp), colors = CardDefaults.cardColors(
            containerColor = clientEntry.color,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // User Icon
            Image(
                painter = painterResource(id = clientEntry.iconResId), // Replace with your image resource
                contentDescription = "User Icon",
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.Gray, shape = CircleShape)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = clientEntry.name, fontSize = 18.sp, color = Color.Black)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(text = clientEntry.starRating.toString())
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Star",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(16.dp)
                    )
                    if (dangerSign) {
                        Text("Posibil sa intarzie", color = Color.Red, fontSize = 12.sp, fontStyle = FontStyle.Italic)
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Accept/Decline buttons
            Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                Text("+${clientEntry.cost}$", color = Color(0xFF006400) , modifier = Modifier.padding(horizontal = 6.dp))
                IconButton(onAccept) {
                    Image(painterResource(R.drawable.accept), contentDescription = null)
                }
                IconButton(onDecline) {
                    Image(painterResource(R.drawable.cancel), contentDescription = null)
                }
            }
        }
    }
}

val clients = listOf(
    ClientEntry(
        name = "Alex",
        cost = "3",
        iconResId = R.drawable.user_icon_2,
        starRating = 4.8f,
        color = Color(0x80E6E6FA)
    ), ClientEntry(
        name = "Teo",
        cost = "4",
        iconResId = R.drawable.user_icon_2,
        starRating = 5f,
        color = Color(0x80FFDAB9)
    ), ClientEntry(
        name = "Doru Terminatoru",
        cost = "8",
        iconResId = R.drawable.user_icon_2,
        starRating = 3.2f,
        color = Color(0x80F08080)
    )
)

// Sample data class for client details
data class ClientEntry(
    val name: String,
    val cost: String,
    val iconResId: Int,  // Replace with the resource ID for user icon
    val starRating: Float,
    val color: Color,
)