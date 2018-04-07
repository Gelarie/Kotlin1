package com.example.kinoversionlast

import com.google.gson.Gson
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.coroutines.experimental.CoroutineContext

fun loadFilmsFromInternet(coroutineContext: CoroutineContext = CommonPool): Deferred<List<Film>> = async(coroutineContext)
{
    val httpClient = OkHttpClient()

    val request = Request.Builder()
            .url("http://85.143.216.90:8082/films")
            .build()

    httpClient.newCall(request).execute().use {
        Gson().fromJson(it.body()!!.string(), Film.List::class.java)
    }

}

fun loadFilmsFromDatabase(app: App, coroutineContext: CoroutineContext = CommonPool): Deferred<List<Film>> = async(coroutineContext)
{
    app.database.filmsDao().getAll()
}

fun saveFilms(app: App, photos: List<Film>, coroutineContext: CoroutineContext = CommonPool): Deferred<Unit> = async(coroutineContext)
{
    app.database.filmsDao().insertAll(photos)
}