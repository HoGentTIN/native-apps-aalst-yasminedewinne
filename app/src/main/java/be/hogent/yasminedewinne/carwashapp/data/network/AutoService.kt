package be.hogent.yasminedewinne.carwashapp.data.network

import be.hogent.yasminedewinne.carwashapp.models.DTO.AutoDTO
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface AutoApiService{

    @GET("auto/gebruiker/{id}")
    fun getAutosForUser(@Path("id")id: Int): Deferred<List<AutoDTO>>
}

object AutoService {
    val HTTP : AutoApiService by lazy { BaseService.RETROFIT.create(AutoApiService::class.java) }
}