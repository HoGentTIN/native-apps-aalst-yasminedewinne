package be.hogent.yasminedewinne.carwashapp.data.network

import be.hogent.yasminedewinne.carwashapp.models.DTO.AfspraakDTO
import be.hogent.yasminedewinne.carwashapp.models.domain.Afspraak
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface AfspraakApiService{

    @GET("afspraken/gebruiker/{id}")
    fun getAfsprakenForUser(@Path("id")id: Int): Deferred<List<Afspraak>>

    @POST("afspraken")
    fun postAfspraak(@Body afspraak: AfspraakDTO): Deferred<AfspraakDTO>

    @DELETE("afspraken/{id}")
    fun deleteAfspraak(@Path("id")id: Int): Deferred<Afspraak>
}

object AfspraakService {
    val HTTP : AfspraakApiService by lazy { BaseService.RETROFIT.create(AfspraakApiService::class.java) }
}