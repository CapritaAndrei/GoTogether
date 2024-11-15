package com.google.maps.android.compose

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.google.android.gms.common.api.Api.Client
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import com.google.maps.android.compose.carpooling.CarPoolingScreenWrapper
import com.google.maps.android.compose.carpooling.ClientEntry
import com.google.maps.android.compose.carpooling.LoadingClientsScreen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

private const val TAG = "AdvancedMarkersActivity"
internal var clients = listOf<ClientEntry>()
internal val loadingPeriod = 5.seconds
class MainActivity : ComponentActivity(), OnMapsSdkInitializedCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(applicationContext, MapsInitializer.Renderer.LATEST, this)
        enableEdgeToEdge()
        val networkRepository = NetworkRepository()
        setContent {
            var isFetchFinished by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(loadingPeriod)
                clients = networkRepository.fetchClientList()
                isFetchFinished = true
            }
            if (isFetchFinished) {
                CarPoolingScreenWrapper()
            } else {
                LoadingClientsScreen()
            }

        }
    }

    override fun onMapsSdkInitialized(renderer: MapsInitializer.Renderer) {
        when (renderer) {
            MapsInitializer.Renderer.LATEST -> Log.d(
                "MapsDemo", "The latest version of the renderer is used."
            )

            MapsInitializer.Renderer.LEGACY -> Log.d(
                "MapsDemo", "The legacy version of the renderer is used."
            )

            else -> {}
        }
    }
}
