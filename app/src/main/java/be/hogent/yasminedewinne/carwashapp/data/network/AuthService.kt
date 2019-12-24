package be.hogent.yasminedewinne.carwashapp.data.network

import be.hogent.yasminedewinne.carwashapp.models.DTO.LoginDTO
import be.hogent.yasminedewinne.carwashapp.models.DTO.RegisterDTO
import be.hogent.yasminedewinne.carwashapp.models.DTO.UserDTO
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    fun loginUser(@Body dto: LoginDTO): Deferred<UserDTO>

    @POST("auth/register")
    fun registerUser(@Body dto: RegisterDTO): Deferred<UserDTO>

    //TODO Implementeer andere Auth methoden
}

object AuthService {
    val HTTP : AuthApiService by lazy { BaseService.RETROFIT.create(AuthApiService::class.java) }
}