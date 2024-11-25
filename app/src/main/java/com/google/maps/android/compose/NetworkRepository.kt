package com.google.maps.android.compose

import android.util.Log
import com.google.maps.android.compose.carpooling.ClientEntry
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class NetworkRepository {
    private val dispatchers = Dispatchers
    private val scope = CoroutineScope(dispatchers.IO)
    private val client = HttpClient(CIO)
    private val deserializer = JsonDeserializer()


    suspend fun fetchClientList(): List<ClientEntry> = withContext(scope.coroutineContext) {
        Log.d("test", "beggining")
        val clientsJson = getClientsJson()
        deserializer.deserialize(clientsJson)
    }

    private suspend fun getClientsJson() = client.request(URI) {
            method = HttpMethod.Get
            contentType(ContentType.Application.Json)
        }.bodyAsText().also { Log.d("answer", it)}

    companion object {
        private const val URI = "http:///192.168.1.3:8080/clients"
    }
}

class JsonDeserializer {
    private val json = Json
    fun deserialize(value: String): List<ClientEntry> {
        return json.decodeFromString(value)
    }
}