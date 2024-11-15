package com.google.maps.android.compose.carpooling

import android.media.Image
import android.opengl.GLSurfaceView.EGLConfigChooser
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.maps.android.compose.R
import kotlinx.coroutines.delay

@Composable
fun CarPoolingScreenWrapper() {
    var chosen by remember { mutableStateOf(false)}

    if (chosen) {
        VictoryScreenWrapper()
    } else {
        ChooseScreen {
            chosen = true
        }
    }
}

@Composable
fun ChooseScreen(onChoose: () -> Unit) {
    var text by remember { mutableStateOf("") }
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().background(color = Color.LightGray), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("GoTogether", fontWeight = FontWeight.ExtraBold, fontSize = 26.sp, modifier = Modifier.padding(top = 60.dp))
        Text("Sofer", modifier = Modifier.padding(20.dp).padding(top = 20.dp), fontSize = 22.sp)
        Image(painterResource(R.drawable.driver), contentDescription = null,
            modifier = Modifier.weight(1f).clickable { onChoose() })
        Text("Vroooomm.....", fontStyle = FontStyle.Italic, fontSize = 15.sp, color = Color.DarkGray)
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text("Plecare") },
            placeholder = { Text("locatie", fontStyle = FontStyle.Italic) }
        )
        TextField(
            value = text1,
            onValueChange = { newText -> text1 = newText },
            label = { Text("Destinatie") },
            placeholder = { Text("locatie", fontStyle = FontStyle.Italic) }
        )
        TextField(
            value = text2,
            onValueChange = { newText -> text2 = newText },
            label = { Text("Ora") },
            placeholder = { Text("Oras", fontStyle = FontStyle.Italic) }
        )


        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black
        )
        Text("Client", modifier = Modifier.padding(48.dp), fontSize = 22.sp)
        Image(painterResource(R.drawable.person), contentDescription = null,
            modifier = Modifier.weight(1f).clickable { onChoose() })
    }
}

@Composable
fun VictoryScreenWrapper() {
    var showVictoryScreen by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize()) {
        CarPoolingScreen(onRideEnd = { showVictoryScreen = true })

        VictoryScreen(
            visible = showVictoryScreen,
            onDismiss = { showVictoryScreen = false }
        )
    }
}

@Composable
fun VictoryScreen(visible: Boolean, onDismiss: () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xCC000000)) // Semi-transparent background
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🎉 Felicitari! 🎉", fontSize = 32.sp, color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))

                // Display money earned
                Text("Bani castigati:", fontSize = 20.sp, color = Color.White)
                Text("$7 ($2 profit)", fontSize = 28.sp, color = Color.Yellow)

                Spacer(modifier = Modifier.height(8.dp))

                // Display time spent
                Text("Timp total:", fontSize = 20.sp, color = Color.White)
                Text("25 minute", fontSize = 28.sp, color = Color.Cyan)

                Spacer(modifier = Modifier.height(8.dp))

                // Display time spent
                Text("Oameni ajutati:", fontSize = 20.sp, color = Color.White)
                Text("2", fontSize = 28.sp, color = Color.Cyan)
                Spacer(modifier = Modifier.height(8.dp))

                // Display time spent
                Text("Carburant salvat:", fontSize = 20.sp, color = Color.White)
                Text("3.6L", fontSize = 28.sp, color = Color.Cyan)
                Text("Inca 10L pana la urmatorul nivel!", fontSize = 20.sp, color = Color.White)

                Spacer(modifier = Modifier.height(24.dp))

                // Dismiss button
                Button(onClick = onDismiss) {
                    Text("Inchide", color = Color.White)
                }
            }
        }
    }
}

@Preview
@Composable
fun VictoryScreenPreview() {
    ChooseScreen() {

    }
}