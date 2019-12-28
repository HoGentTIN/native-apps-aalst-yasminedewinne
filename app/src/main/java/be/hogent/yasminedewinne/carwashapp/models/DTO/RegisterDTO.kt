package be.hogent.yasminedewinne.carwashapp.models.DTO

import be.hogent.yasminedewinne.carwashapp.models.domain.Adres
import com.squareup.moshi.Json

data class RegisterDTO(
    //var afbeelding: String?,

    var voornaam: String,

    var familienaam: String,

    var email: String,

    @Json(name = "TelefoonNr")
    var telNr: String,

    var adres: Adres,

    var wachtwoord: String,

    var wachtwoordHerhaling: String
)