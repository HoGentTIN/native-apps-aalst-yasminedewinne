package be.hogent.yasminedewinne.carwashapp.models.DTO

import com.squareup.moshi.Json

data class UserDTO(
    @Json(name = "token")
    val authToken: String

    // val afbeelding: String?
)
