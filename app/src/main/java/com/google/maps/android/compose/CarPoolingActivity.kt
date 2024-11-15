package com.google.maps.android.compose

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import com.google.maps.android.compose.carpooling.CarPoolingScreenWrapper

private const val TAG = "AdvancedMarkersActivity"




class CarPooling : ComponentActivity(), OnMapsSdkInitializedCallback {

    @SuppressLint("SetTextI18n", "UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(applicationContext, MapsInitializer.Renderer.LATEST, this)
        enableEdgeToEdge()
        setContent {
            CarPoolingScreenWrapper()
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
