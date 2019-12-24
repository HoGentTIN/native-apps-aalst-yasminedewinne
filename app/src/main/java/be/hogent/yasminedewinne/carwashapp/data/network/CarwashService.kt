package be.hogent.yasminedewinne.carwashapp.data.network

import be.hogent.yasminedewinne.carwashapp.models.domain.Carwash
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface CarwashApiService{

    @GET("carwashes")
    fun getAllCarwashes(): Deferred<List<Carwash>>

    @GET("carwashes/stad")
    fun getCarwashesByStad(@Path("stad")stad: String): Deferred<List<Carwash>>

    @POST("carwashes")
    fun postCarwash(@Body carwash: Carwash): Deferred<Carwash>

    @DELETE("carwashes/{id}")
    fun deleteCarwash(@Path("id") id: Int): Deferred<Carwash>
}

object CarwashService {
    val HTTP : CarwashApiService by lazy { BaseService.RETROFIT.create(CarwashApiService::class.java) }
}

